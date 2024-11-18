package com.example.studysyncapp.data.mapper

import android.util.Log
import com.example.studysyncapp.domain.model.AppError

fun Throwable.toAppError(): AppError{
    Log.e("MYTAG", "App Error: ${this}")
    val message = "Something went wrong"
    return AppError(message, this)
    TODO("Implement More specific messages using when block")

}