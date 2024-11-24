package com.example.studysyncapp.presentation.classroom.list

import com.example.studysyncapp.domain.model.Classroom

data class ClassroomsViewState(
    val classrooms: List<Classroom> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showCreateClassroomDialog: Boolean = false,
    val showJoinClassroomDialog: Boolean = false,
)
