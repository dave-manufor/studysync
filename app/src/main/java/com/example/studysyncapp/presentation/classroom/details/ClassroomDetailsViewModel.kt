package com.example.studysyncapp.presentation.classroom.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.data.repository.AssignmentsRepositoryImpl
import com.example.studysyncapp.data.repository.ClassroomsRepositoryImpl
import com.example.studysyncapp.data.repository.CoursesRepositoryImpl
import com.example.studysyncapp.data.repository.EventsRepositoryImpl
import com.example.studysyncapp.data.repository.SchedulesRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Classroom
import com.example.studysyncapp.domain.repository.AssignmentsRepository
import com.example.studysyncapp.domain.repository.ClassroomsRepository
import com.example.studysyncapp.domain.repository.CoursesRepository
import com.example.studysyncapp.domain.repository.EventsRepository
import com.example.studysyncapp.domain.repository.SchedulesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ClassroomDetailsViewModel(private val classroomId: String): ViewModel() {
    private val coursesRepository: CoursesRepository = CoursesRepositoryImpl()
    private val assignmentsRepository: AssignmentsRepository = AssignmentsRepositoryImpl()
    private val schedulesRepository: SchedulesRepository = SchedulesRepositoryImpl()
    private val eventsRepository: EventsRepository = EventsRepositoryImpl()
    private val classroomRepository: ClassroomsRepository = ClassroomsRepositoryImpl()
    private val _state = MutableStateFlow(ClassroomViewState())
    val state: StateFlow<ClassroomViewState> = _state.asStateFlow()

    init{
        Log.d("MYTAG", "ClassroomDetailsViewModel init called")
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            async{ getClassroom() }.await().let {
                checkEditAuthority()
                async { getCourses() }
                async { getAssignments() }
                async { getSchedules() }
                async { getEvents() }
            }
        }
    }

    private fun setError(error: AppError){
        _state.update { it.copy(error = error.message, isLoading = false) }
    }

    private fun setLoading(isLoading: Boolean){
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun setClassroom(classroom: Classroom?){
        if(classroom == null){
            setError(AppError(message = "Classroom not found"))
            return
        }
        _state.update { it.copy(classroom = classroom, isLoading = false) }
    }

    suspend fun getClassroom(){
        val classroom = classroomRepository.getClassroomById(classroomId)
        when(classroom){
            is Either.Left -> setError(classroom.value)
            is Either.Right -> setClassroom(classroom.value)
        }
    }

    suspend fun getCourses(){
        val courses = coursesRepository.getCoursesByClassroomId(classroomId)
        when(courses){
            is Either.Left -> setError(courses.value)
            is Either.Right -> _state.update { it.copy(courses = courses.value) }
        }
    }

    suspend fun getAssignments(){
        val assignments = assignmentsRepository.getAssignmentsByClassroomId(classroomId)
        when(assignments){
            is Either.Left -> setError(assignments.value)
            is Either.Right -> _state.update { it.copy(assignments = assignments.value) }
        }
    }

    suspend fun getSchedules(){
        val schedules = schedulesRepository.getSchedulesByClassroomId(classroomId)
        when(schedules){
            is Either.Left -> setError(schedules.value)
            is Either.Right -> _state.update { it.copy(schedules = schedules.value) }
        }
    }

    suspend fun getEvents(){
        val events = eventsRepository.getEventsByClassroomId(classroomId)
        when(events){
            is Either.Left -> setError(events.value)
            is Either.Right -> _state.update { it.copy(events = events.value) }
        }
    }

    suspend fun checkEditAuthority(){
        val userId = getUserId()
        _state.update { it.copy(isEditable = state.value.classroom?.user_id == userId) }
    }
}