package com.example.studysyncapp.presentation.agenda_screen

import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.event.Event

data class AgendaViewState(
    val assignments: List<Assignment> = emptyList(),
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val isMenuOpen: Boolean = false,
    val error: String? = null,
    val showCreateEventDialog: Boolean = false
)
