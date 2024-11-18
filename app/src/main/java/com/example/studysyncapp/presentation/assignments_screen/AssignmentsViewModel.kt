package com.example.studysyncapp.presentation.assignments_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.data.repository.AssignmentsRepositoryImpl
import com.example.studysyncapp.data.repository.CoursesRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.repository.AssignmentsRepository
import com.example.studysyncapp.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AssignmentsViewModel(): ViewModel() {
    private val assignmentsRepository: AssignmentsRepository = AssignmentsRepositoryImpl()
    private val coursesRepository: CoursesRepository = CoursesRepositoryImpl()
    private val _state = MutableStateFlow(AssignmentsViewState())
    val state: StateFlow<AssignmentsViewState> = _state.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            getAssignments()
            getCourses()
        }
    }

    private fun setAssignments(assignments: List<Assignment>){
        _state.update { it.copy(assignments = assignments, isLoading = false) }
    }

    private fun setCourses(courses: List<Course>){
        _state.update { it.copy(courses = courses, isLoading = false) }
    }

    private fun setError(error: AppError){
        _state.update { it.copy(error = error.message, isLoading = false) }
    }

    private fun setLoading(isLoading: Boolean){
        _state.update { it.copy(isLoading = isLoading) }
    }

    suspend fun getAssignments(){
        val assignmentList = assignmentsRepository.getAssignments()
        when(assignmentList){
            is Either.Left -> {
                setError(assignmentList.value)
            }
            is Either.Right -> {
                setAssignments(assignmentList.value)
            }
        }
    }

    suspend fun getCourses(){
        val courses = coursesRepository.getCourses()
        when(courses){
            is Either.Left -> setError(courses.value)
            is Either.Right -> setCourses(courses.value)
        }
    }

    suspend fun createAssignment(assignment: Assignment){
        val newAssignment = assignmentsRepository.insertAssignment(assignment)
        when(newAssignment){
            is Either.Left -> {
                setError(newAssignment.value)
            }
            is Either.Right -> {
                getAssignments()
            }
        }
    }

    fun onConfirmCreateAssignment(assignmentName: String, assignmentDescription: String, courseId: String, dueAt: String, classroomId: String? = null){
        viewModelScope.launch(Dispatchers.IO){
            createAssignment(Assignment(id = UUID.randomUUID().toString(), name = assignmentName, description = assignmentDescription, course_id = courseId, due_at = dueAt, user_id = getUserId(), classroom_id = classroomId))
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateAssignmentDialog = false) }
            }
        }
    }

    fun onOpenCreateAssignment(){
        _state.update { it.copy(showCreateAssignmentDialog = true) }
    }

    fun onDismissCreateAssignment(){
        _state.update { it.copy(showCreateAssignmentDialog = false) }
    }
}