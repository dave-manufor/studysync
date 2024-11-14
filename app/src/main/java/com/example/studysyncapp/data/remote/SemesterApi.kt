package com.example.studysyncapp.data.remote

import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.Semester
import io.github.jan.supabase.postgrest.from

class SemesterApi {
    private val supabase = Supabase.getInstance()

    suspend fun getSemesters(): List<Semester>{
        val semesters = supabase.from("semesters").select().decodeList<Semester>()
        return semesters
    }

    suspend fun insertSemester(semester: Semester): Semester{
        val newSemester = supabase.from("semesters").insert(semester){
            select()
        }.decodeSingle<Semester>()
        return newSemester
    }

    suspend fun updateSemester(semester: Semester): Semester{
        val updatedSemester = supabase.from("semesters").update(semester){
            select()
            filter {
                eq("id", semester.id)
            }
        }.decodeSingle<Semester>()
        return updatedSemester
    }

    suspend fun deleteSemester(semester: Semester): Semester {
        val deletedSemester = supabase.from("semesters").delete {
            select()
            filter {
                eq("id", semester.id)
            }
        }.decodeSingle<Semester>()
        return deletedSemester
    }
}