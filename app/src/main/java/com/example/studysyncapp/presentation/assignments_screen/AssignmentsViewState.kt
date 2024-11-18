package com.example.studysyncapp.presentation.assignments_screen

import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Course

data class AssignmentsViewState(
    val assignments: List<Assignment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showCreateAssignmentDialog: Boolean = false,
    val courses: List<Course> = emptyList()
)
