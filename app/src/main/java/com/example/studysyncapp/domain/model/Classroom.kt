package com.example.studysyncapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Classroom(
    val id: String,
    val user_id: String,
    val name: String,
    val color: String,
    val code: String? = null,
    val invite_only: Boolean,
    val description: String?
)