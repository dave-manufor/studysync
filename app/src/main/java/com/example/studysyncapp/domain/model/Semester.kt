package com.example.studysyncapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Semester(
    val id: String,
    val name: String,
    val userId: String,
    val classroomId: String,
    val startsAt: String,
    val endsAt: String,
    val createdAt: String,
)
