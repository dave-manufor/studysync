package com.example.studysyncapp.presentation.courses

import com.example.studysyncapp.domain.model.Course

data class CoursesViewState(
    val courses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showCreateCourseDialog: Boolean = false,
    val showCreateScheduleDialog: Boolean = false
)
