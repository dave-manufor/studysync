package com.example.studysyncapp.presentation.classroom.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClassroomDetailsViewModelFactory(private val id: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassroomDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClassroomDetailsViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
