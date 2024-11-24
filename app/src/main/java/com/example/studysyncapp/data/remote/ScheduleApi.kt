package com.example.studysyncapp.data.remote

import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.Schedule
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class ScheduleApi {
    private val supabase = Supabase.getInstance()
    private val scheduleTable = supabase.from("schedules")

    suspend fun getSchedule(): List<Schedule>{
        val schedules = scheduleTable.select(Columns.raw("*, course:courses(*)")).decodeList<Schedule>()
        return schedules
    }

    suspend fun getSchedulesByClassroomId(classroomId: String): List<Schedule>{
        val schedules = scheduleTable.select(Columns.raw("*, course:courses(*)")){
            filter {
                eq("classroom_id", classroomId)
            }
        }.decodeList<Schedule>()
        return schedules
    }

    suspend fun insertSchedule(schedule: Schedule): Schedule {
        val newSchedule = scheduleTable.insert(schedule) {
            select(Columns.raw("*, course:courses(*)"))
        }.decodeSingle<Schedule>()
        return newSchedule
    }

    suspend fun updateSchedule(schedule: Schedule): Schedule {
        val updatedSchedule = scheduleTable.update(schedule) {
            select(Columns.raw("*, course:courses(*)"))
            filter {
                eq("id", schedule.id)
            }
        }.decodeSingle<Schedule>()
        return updatedSchedule
    }

    suspend fun deleteSchedule(id: String): Schedule {
        val deletedSchedule = scheduleTable.delete {
            select(Columns.raw("*, course:courses(*)"))
            filter {
                eq("id", id)
            }
        }.decodeSingle<Schedule>()
        return deletedSchedule
    }
}