package com.example.studysyncapp.presentation.classroom.details

import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Classroom
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.model.event.Event
import com.example.studysyncapp.domain.model.file.File

data class ClassroomViewState(
    val isEditable: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val classroom: Classroom? = null,
    val userId: String? = null,
    val courses: List<Course> = emptyList(),
    val assignments: List<Assignment> = emptyList(),
    val schedules: List<Schedule> = emptyList(),
    val events: List<Event> = emptyList(),
    val files: List<File> = emptyList(),
    val showUploadFileDialog: Boolean = false,
    val showCreateScheduleDialog: Boolean = false,
    val showCreateEventDialog: Boolean = false,
    val showCreateAssignmentDialog: Boolean = false,
    val showCreateCourseDialog: Boolean = false,
)
