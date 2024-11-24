package com.example.studysyncapp.presentation.classroom.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.data.repository.ClassroomsRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Classroom
import com.example.studysyncapp.domain.repository.ClassroomsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ClassroomsViewModel: ViewModel(), CreateClassroomDialogController,
    JoinClassroomDialogController {
    private val classroomsRepository: ClassroomsRepository = ClassroomsRepositoryImpl()
    private val _state = MutableStateFlow(ClassroomsViewState())
    val state: StateFlow<ClassroomsViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            setLoading(true)
            getClassrooms()
        }
    }

    private fun setClassrooms(classrooms: List<Classroom>){
        _state.update { it.copy(classrooms = classrooms, isLoading = false) }
    }

    private fun setError(error: AppError){
        _state.update { it.copy(error = error.message, isLoading = false) }
    }

    private fun setLoading(isLoading: Boolean){
        _state.update { it.copy(isLoading = isLoading) }
    }

    suspend fun getClassrooms(){
        val classrooms = classroomsRepository.getClassrooms()
        when(classrooms){
            is Either.Left -> setError(classrooms.value)
            is Either.Right -> setClassrooms(classrooms.value)
        }
    }

    suspend fun createClassroom(classroom: Classroom){
        val newClassroom = classroomsRepository.insertClassroom(classroom)
        when(newClassroom){
            is Either.Left -> setError(newClassroom.value)
            is Either.Right -> getClassrooms()
        }
    }

    override fun onConfirmCreateClassroom(
        name: String,
        colorString: String,
        description: String?,
        inviteOnly: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            createClassroom(
                Classroom(
                    id = UUID.randomUUID().toString(),
                    user_id = getUserId(),
                    name = name,
                    color = colorString,
                    invite_only = inviteOnly,
                    description = description
                )
            )
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateClassroomDialog = false) }
            }
        }
    }

    override fun onDismissCreateClassroom() {
        _state.update { it.copy(showCreateClassroomDialog = false) }
    }

    override fun onOpenCreateClassroom() {
        _state.update { it.copy(showCreateClassroomDialog = true) }
    }

    override fun onOpenJoinClassroom() {
        _state.update { it.copy(showJoinClassroomDialog = true) }
    }

    override fun onDismissJoinClassroom() {
        _state.update { it.copy(showJoinClassroomDialog = false) }
    }

    override fun onConfirmJoinClassroom(classCode: String) {
        viewModelScope.launch(Dispatchers.IO){
            val register = classroomsRepository.registerUser(userId = getUserId(), classCode = classCode)
            when(register){
                is Either.Left -> setError(register.value)
                is Either.Right -> {
                    _state.update { it.copy(showJoinClassroomDialog = false) }
                    getClassrooms()
                }
            }
        }
    }


    fun onConfirmLeaveClassroom(classCode: String){
        viewModelScope.launch(Dispatchers.IO){
            val classroom = classroomsRepository.unRegisterUser(userId = getUserId(), classCode = classCode)
            when(classroom){
                is Either.Left -> setError(classroom.value)
                is Either.Right -> getClassrooms()
            }
        }
    }
}