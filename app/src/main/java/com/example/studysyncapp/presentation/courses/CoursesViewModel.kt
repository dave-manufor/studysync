package com.example.studysyncapp.presentation.courses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.data.repository.CoursesRepositoryImpl
import com.example.studysyncapp.data.repository.SchedulesRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.repository.CoursesRepository
import com.example.studysyncapp.domain.repository.SchedulesRepository
import com.example.studysyncapp.utils.DaysOfTheWeek
import com.example.studysyncapp.utils.toUTC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.UUID

class CoursesViewModel(): ViewModel(), CreateCourseDialogController, CreateScheduleDialogController {
    private val coursesRepository: CoursesRepository = CoursesRepositoryImpl()
    private val schedulesRepository: SchedulesRepository = SchedulesRepositoryImpl()
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

    override fun onConfirmCreateCourse(courseName: String, courseColor: String, courseDescription: String, classroomId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            createCourse(Course(id = UUID.randomUUID().toString(),name = courseName, color = courseColor, description = courseDescription, user_id = getUserId(), classroom_id = classroomId))
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateCourseDialog = false) }
            }
        }
    }

    override fun onConfirmCreateSchedule(course: Course, day: DaysOfTheWeek, startTime: LocalTime, endTime: LocalTime, online: Boolean, location: String?, classroomId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            schedulesRepository.insertSchedule(
                Schedule(
                    id = UUID.randomUUID().toString(), course_id = course.id,
                    user_id = getUserId(),
                    classroom_id = classroomId,
                    day_of_the_week = day.value,
                    starts_at = startTime.toUTC(),
                    ends_at = endTime.toUTC(),
                    online = online,
                    location = location,
                )
            )
            if(_state.value.error.isNullOrEmpty()){
                _state.update { it.copy(showCreateScheduleDialog = false) }
            }
        }
    }


    override fun onOpenCreateCourse() {
        _state.update { it.copy(showCreateCourseDialog = true) }
    }

    override fun onDismissCreateCourse() {
        _state.update { it.copy(showCreateCourseDialog = false) }
    }

    override fun onOpenCreateSchedule(){
        _state.update { it.copy(showCreateScheduleDialog = true) }
    }

    override fun onDismissCreateSchedule(){
        _state.update { it.copy(showCreateScheduleDialog = false) }
    }
}