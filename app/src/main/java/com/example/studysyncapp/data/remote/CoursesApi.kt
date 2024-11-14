package com.example.studysyncapp.data.remote

import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.Course
import io.github.jan.supabase.postgrest.from

class CoursesApi {
    private val supabase = Supabase.getInstance()

    suspend fun getCourses(): List<Course>{
        val course = supabase.from("courses").select().decodeList<Course>()
        return course
    }

    suspend fun insertCourse(course: Course): Course{
        val newCourse = supabase.from("courses").insert(course){
            select()
        }.decodeSingle<Course>()
        return newCourse
    }

    suspend fun updateCourse(course: Course): Course{
        val updatedCourse = supabase.from("courses").update(course){
            select()
            filter {
                eq("id", course.id)
            }
        }.decodeSingle<Course>()
        return updatedCourse
    }

    suspend fun deleteCourse(course: Course): Course{
        val deletedCourse = supabase.from("courses").delete{
            select()
            filter {
                eq("id", course.id)
            }
        }.decodeSingle<Course>()

        return deletedCourse
    }

}