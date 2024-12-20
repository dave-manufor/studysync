package com.example.studysyncapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String,
    val name: String,
    val user_id: String?,
    val classroom_id: String?,
    val description: String?,
    val color: String,
)
