package com.example.studysyncapp.data.repository

import arrow.core.Either
import com.example.studysyncapp.data.mapper.toAppError
import com.example.studysyncapp.data.remote.EventsApi
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.event.Event
import com.example.studysyncapp.domain.repository.EventsRepository

class EventsRepositoryImpl: EventsRepository {
    private val eventsApi = EventsApi()

    override suspend fun getEvents(): Either<AppError, List<Event>> {
        return Either.catch {
            eventsApi.getEvents()
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun getEventsByDate(date: String): Either<AppError, List<Event>> {
        return Either.catch {
            eventsApi.getEventsByDate(date)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun insertEvent(event: Event): Either<AppError, Event> {
        return Either.catch {
            eventsApi.insertEvent(event)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun updateEvent(event: Event): Either<AppError, Event> {
        return Either.catch {
            eventsApi.updateEvent(event)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun deleteEvent(id: String): Either<AppError, Event> {
        return Either.catch {
            eventsApi.deleteEvent(id)
        }.mapLeft {
            it.toAppError()
        }
    }
}