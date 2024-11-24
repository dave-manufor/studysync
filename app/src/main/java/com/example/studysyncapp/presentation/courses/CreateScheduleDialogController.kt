package com.example.studysyncapp.presentation.courses

import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.utils.DaysOfTheWeek
import java.time.LocalTime

interface CreateScheduleDialogController{
    abstract fun onOpenCreateSchedule()
    abstract fun onDismissCreateSchedule()
    abstract fun onConfirmCreateSchedule(course: Course, day: DaysOfTheWeek, startTime: LocalTime, endTime: LocalTime, online: Boolean, location: String? = null, classroomId: String? = null)
}