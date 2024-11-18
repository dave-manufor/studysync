package com.example.studysyncapp.presentation.courses_screen

import com.example.studysyncapp.domain.model.Course

data class CoursesViewState(
    val courses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showCreateCourseDialog: Boolean = false
)
