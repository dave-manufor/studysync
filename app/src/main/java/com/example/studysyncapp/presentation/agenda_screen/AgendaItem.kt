package com.example.studysyncapp.presentation.agenda_screen

import androidx.compose.ui.graphics.Color
import java.util.Date


data class AgendaItem(
    val type: AgendaType,
    val sortDate: Date,
    val timeString: String,
    val title: String,
    val subText: String,
    val tint: Color
)

enum class AgendaType(val value: String) {
    EVENT("event"),
    TEST("test"),
    EXAM("exam"),
    ASSIGNMENT("assignment");

    override fun toString(): String {
        return this.value
    }
}