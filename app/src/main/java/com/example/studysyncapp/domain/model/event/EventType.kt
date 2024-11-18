package com.example.studysyncapp.domain.model.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EventType(val value: String) {
    @SerialName("event") EVENT("event"),
    @SerialName("test") TEST("test"),
    @SerialName("exam") EXAM("exam");

    override fun toString(): String = value
}