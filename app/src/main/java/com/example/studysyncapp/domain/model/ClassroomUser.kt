package com.example.studysyncapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ClassroomUser(
    val classroom_code: String,
    val user_id: String,
)
