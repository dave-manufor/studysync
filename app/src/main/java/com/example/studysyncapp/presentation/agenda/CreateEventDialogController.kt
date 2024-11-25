package com.example.studysyncapp.presentation.agenda

import androidx.lifecycle.viewModelScope
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.domain.model.event.Event
import com.example.studysyncapp.domain.model.event.EventType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

interface CreateEventDialogController {
    fun onConfirmCreateEvent(eventName: String, eventType: EventType, eventDescription: String, eventStartDate: String, eventEndDate: String, classroomId: String? = null)

    fun onOpenCreateEvent()

    fun onDismissCreateEvent()
}