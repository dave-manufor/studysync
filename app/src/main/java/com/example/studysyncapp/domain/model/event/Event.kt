package com.example.studysyncapp.domain.model.event

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val name: String,
    val user_id: String?,
    val classroom_id: String? = null,
    val starts_at: String,
    val ends_at: String,
    val description: String?,
    val type: EventType
)
