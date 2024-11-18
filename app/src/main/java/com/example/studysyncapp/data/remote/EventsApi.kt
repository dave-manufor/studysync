package com.example.studysyncapp.data.remote

import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.event.Event
import io.github.jan.supabase.postgrest.from

class EventsApi {
    private val supabase = Supabase.getInstance()
    private val eventsTable = supabase.from("events")

    suspend fun getEvents(): List<Event>{
        val events = eventsTable.select().decodeList<Event>()
        return events
    }

    suspend fun getEventsByDate(date: String): List<Event>{
        val events = eventsTable.select{
            filter {
                eq("starts_at", date)
            }
        }.decodeList<Event>()
        return events
    }

    suspend fun insertEvent(event: Event): Event{
        val newEvent = eventsTable.insert(event){
            select()
        }.decodeSingle<Event>()
        return newEvent
    }

    suspend fun updateEvent(event: Event): Event{
        val updatedEvent = eventsTable.update(event) {
            select()
            filter {
                eq("id", event.id)
            }
        }.decodeSingle<Event>()
        return updatedEvent
    }

    suspend fun deleteEvent(id: String): Event{
        val deletedEvent = eventsTable.delete {
            select()
            filter {
                eq("id", id)
            }
        }.decodeSingle<Event>()
        return deletedEvent
    }
}