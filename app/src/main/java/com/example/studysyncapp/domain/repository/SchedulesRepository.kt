package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Schedule

interface SchedulesRepository {
    suspend fun getSchedules(): Either<AppError, List<Schedule>>
    suspend fun getSchedulesByClassroomId(id: String): Either<AppError, List<Schedule>>
    suspend fun insertSchedule(schedule: Schedule): Either<AppError, Schedule>
    suspend fun updateSchedule(schedule: Schedule): Either<AppError, Schedule>
    suspend fun deleteSchedule(id: String): Either<AppError, Schedule>
}