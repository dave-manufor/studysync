package com.example.studysyncapp.presentation.agenda

import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.model.event.Event

data class AgendaViewState(
    val assignments: List<Assignment> = emptyList(),
    val events: List<Event> = emptyList(),
    val schedule: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val isMenuOpen: Boolean = false,
    val error: String? = null,
    val showCreateEventDialog: Boolean = false
)
