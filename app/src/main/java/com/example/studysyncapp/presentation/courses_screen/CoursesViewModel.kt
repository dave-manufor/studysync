package com.example.studysyncapp.presentation.courses_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.data.repository.CoursesRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class CoursesViewModel(): ViewModel() {
    private val coursesRepository: CoursesRepository = CoursesRepositoryImpl()
    private val _state = MutableStateFlow(CoursesViewState())
    val state: StateFlow<CoursesViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            getCourses()
        }
    }

    private fun setCourses(courses: List<Course>) {
        _state.update { it.copy(courses = courses, isLoading = false) }
    }

    private fun setError(error: AppError) {
        _state.update { it.copy(error = error.message, isLoading = false) }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }


    suspend fun getCourses() {
        val courseList = coursesRepository.getCourses()
        when (courseList) {
            is Either.Left -> {
                Log.e("MYTAG", "getCourses: ${courseList.value}")
                setError(courseList.value)
            }

            is Either.Right -> {
                Log.d("MYTAG", "getCourses: ${courseList.value}")
                setCourses(courseList.value)
            }
        }
    }

    suspend fun createCourse(course: Course) {
        val newCourse = coursesRepository.insertCourse(course)
        when (newCourse) {
            is Either.Left -> {
                setError(newCourse.value)
            }

            is Either.Right -> {
                getCourses()
            }
        }
    }

    fun onConfirmCreateCourse(courseName: String, courseColor: String, courseDescription: String, classroomId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            createCourse(Course(id = UUID.randomUUID().toString(),name = courseName, color = courseColor, description = courseDescription, user_id = getUserId(), classroom_id = classroomId))
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateCourseDialog = false) }
            }
        }
    }

    fun onOpenCreateCourse() {
        _state.update { it.copy(showCreateCourseDialog = true) }
    }

    fun onDismissCreateCourse() {
        _state.update { it.copy(showCreateCourseDialog = false) }
    }
}