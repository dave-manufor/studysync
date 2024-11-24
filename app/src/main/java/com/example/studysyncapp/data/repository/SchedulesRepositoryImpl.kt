package com.example.studysyncapp.data.repository

import arrow.core.Either
import com.example.studysyncapp.data.mapper.toAppError
import com.example.studysyncapp.data.remote.ScheduleApi
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.repository.SchedulesRepository

class SchedulesRepositoryImpl: SchedulesRepository {
    private val scheduleApi = ScheduleApi()
    override suspend fun getSchedules(): Either<AppError, List<Schedule>> {
        return Either.catch {
            scheduleApi.getSchedule()
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun getSchedulesByClassroomId(id: String): Either<AppError, List<Schedule>> {
        return Either.catch {
            scheduleApi.getSchedulesByClassroomId(id)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun insertSchedule(schedule: Schedule): Either<AppError, Schedule> {
        return Either.catch {
            scheduleApi.insertSchedule(schedule)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun updateSchedule(schedule: Schedule): Either<AppError, Schedule> {
        return Either.catch{
            scheduleApi.updateSchedule(schedule)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun deleteSchedule(id: String): Either<AppError, Schedule> {
        return Either.catch{
            scheduleApi.deleteSchedule(id)
        }.mapLeft {
            it.toAppError()
        }
    }

}