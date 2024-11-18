package com.example.studysyncapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val id: String,
    val name: String,
    val course_id: String,
    val course: Course? = null,
    val user_id: String?,
    val classroom_id: String? = null,
    val description: String?,
    val due_at: String,
)
