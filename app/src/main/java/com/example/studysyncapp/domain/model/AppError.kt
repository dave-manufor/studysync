package com.example.studysyncapp.domain.model

data class AppError(val message: String, val cause: Throwable? = null)
