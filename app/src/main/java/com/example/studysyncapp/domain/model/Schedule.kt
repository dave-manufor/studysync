package com.example.studysyncapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val id: String,
    val course_id: String,
    val course: Course? = null,
    val user_id: String?,
    val classroom_id: String? = null,
    val day_of_the_week: Int,
    val starts_at: String,
    val ends_at: String,
    val online: Boolean,
    val location: String? = null
)
