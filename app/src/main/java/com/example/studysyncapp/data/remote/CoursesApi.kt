package com.example.studysyncapp.data.remote

import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.Course
import io.github.jan.supabase.postgrest.from

class CoursesApi {
    private val supabase = Supabase.getInstance()
    private val coursesTable = supabase.from("courses")

    suspend fun getCourses(): List<Course>{
        val courses = coursesTable.select().decodeList<Course>()
        return courses
    }

    suspend fun getCoursesByClassroomId(classroomId: String): List<Course>{
        val courses = coursesTable.select{
            filter {
                eq("classroom_id", classroomId)
            }
        }.decodeList<Course>()
        return courses
    }

    suspend fun insertCourse(course: Course): Course{
        val newCourse = coursesTable.insert(course){
            select()
        }.decodeSingle<Course>()
        return newCourse
    }

    suspend fun updateCourse(course: Course): Course{
        val updatedCourse = coursesTable.update(course){
            select()
            filter {
                eq("id", course.id)
            }
        }.decodeSingle<Course>()
        return updatedCourse
    }

    suspend fun deleteCourse(id: String): Course{
        val deletedCourse = coursesTable.delete{
            select()
            filter {
                eq("id", id)
            }
        }.decodeSingle<Course>()

        return deletedCourse
    }

}