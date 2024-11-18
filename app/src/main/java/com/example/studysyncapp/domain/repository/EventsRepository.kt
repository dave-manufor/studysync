package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.event.Event

interface EventsRepository {
    suspend fun getEvents(): Either<AppError, List<Event>>
    suspend fun getEventsByDate(date: String): Either<AppError, List<Event>>
    suspend fun insertEvent(event: Event): Either<AppError, Event>
    suspend fun updateEvent(event: Event): Either<AppError, Event>
    suspend fun deleteEvent(id: String): Either<AppError, Event>
}