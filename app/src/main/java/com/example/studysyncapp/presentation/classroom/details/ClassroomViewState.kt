package com.example.studysyncapp.presentation.classroom.details

import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Classroom
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.model.event.Event

data class ClassroomViewState(
    val isEditable: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val classroom: Classroom? = null,
    val userId: String? = null,
    val courses: List<Course> = emptyList(),
    val assignments: List<Assignment> = emptyList(),
    val schedules: List<Schedule> = emptyList(),
    val events: List<Event> = emptyList()
)
