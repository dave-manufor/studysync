package com.example.studysyncapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String,
    val name: String,
    val userId: String,
    val classroomId: String,
    val semesterId: String,
    val color: String,
    val createdAt: String,
)
