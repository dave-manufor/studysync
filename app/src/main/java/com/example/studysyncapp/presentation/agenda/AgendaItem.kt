package com.example.studysyncapp.presentation.agenda

import androidx.compose.ui.graphics.Color
import com.example.studysyncapp.utils.DaysOfTheWeek
import java.util.Date


data class AgendaItem(
    val type: AgendaType,
    val day: DaysOfTheWeek? = null,
    val sortDate: Date,
    val timeString: String,
    val title: String,
    val subText: String,
    val isLink: Boolean = false,
    val tint: Color
)

enum class AgendaType(val value: String) {
    EVENT("event"),
    TEST("test"),
    EXAM("exam"),
    ASSIGNMENT("assignment"),
    SCHEDULE("schedule");

    override fun toString(): String {
        return this.value
    }
}