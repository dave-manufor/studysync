package com.example.studysyncapp.utils

enum class DaysOfTheWeek(val value: Int) {
    SUNDAY(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    override fun toString(): String = when(this){
        SUNDAY -> "sunday"
        MONDAY -> "monday"
        TUESDAY -> "tuesday"
        WEDNESDAY -> "wednesday"
        THURSDAY -> "thursday"
        FRIDAY -> "friday"
        SATURDAY -> "saturday"
    }

    companion object {
        fun fromInt(value: Int) = entries.first { it.value == value }
    }
}