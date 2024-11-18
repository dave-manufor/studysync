package com.example.studysyncapp.presentation.agenda_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.data.repository.AssignmentsRepositoryImpl
import com.example.studysyncapp.data.repository.EventsRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.event.Event
import com.example.studysyncapp.domain.model.event.EventType
import com.example.studysyncapp.domain.repository.AssignmentsRepository
import com.example.studysyncapp.domain.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AgendaViewModel(): ViewModel() {
    private val assignmentsRepository: AssignmentsRepository = AssignmentsRepositoryImpl()
    private val eventsRepository: EventsRepository = EventsRepositoryImpl()
    private var _state = MutableStateFlow(AgendaViewState())
    val state = _state.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            getAssignments()
            getEvents()
        }
    }

    private fun setAssignments(assignments: List<Assignment>){
        _state.update { it.copy(assignments = assignments, isLoading = false) }
    }

    private fun setEvents(events: List<Event>){
        _state.update { it.copy(events = events, isLoading = false) }
    }

    private fun setError(error: AppError){
        _state.update { it.copy(error = error.message, isLoading = false) }
    }

    private fun setLoading(isLoading: Boolean){
        _state.update { it.copy(isLoading = isLoading) }
    }

    suspend fun getAssignments(){
        val assignments = assignmentsRepository.getAssignments()
        when(assignments){
            is Either.Left -> {
                setError(assignments.value)
            }
            is Either.Right -> {
                setAssignments(assignments.value)
            }
        }
    }

    suspend fun getEvents(){
        val events = eventsRepository.getEvents()
        when(events){
            is Either.Left -> {
                setError(events.value)
            }
            is Either.Right -> {
                setEvents(events.value)
            }
        }
    }

    suspend fun createEvent(event: Event){
        val newEvent = eventsRepository.insertEvent(event)
        when(newEvent){
            is Either.Left -> {
                setError(newEvent.value)
            }
            is Either.Right -> {
                getEvents()
            }
        }
    }

    fun onConfirmCreateEvent(eventName: String, eventType: EventType, eventDescription: String, eventStartDate: String, eventEndDate: String, classroomId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            createEvent(
                Event(
                    id = UUID.randomUUID().toString(),
                    name = eventName,
                    type = eventType,
                    description = eventDescription,
                    user_id = getUserId(),
                    classroom_id = classroomId,
                    starts_at = eventStartDate,
                    ends_at = eventEndDate,
                )
            )
            if (_state.value.error.isNullOrEmpty()) {
                _state.update { it.copy(showCreateEventDialog = false) }
            }
        }
    }

    fun onOpenCreateEvent(){
        _state.update { it.copy(showCreateEventDialog = true) }
    }

    fun onDismissCreateEvent(){
        _state.update { it.copy(showCreateEventDialog = false) }
    }
}