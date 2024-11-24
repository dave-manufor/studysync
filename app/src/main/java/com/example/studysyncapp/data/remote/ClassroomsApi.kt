package com.example.studysyncapp.data.remote

import android.util.Log
import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.Classroom
import com.example.studysyncapp.domain.model.ClassroomUser
import io.github.jan.supabase.postgrest.from

class ClassroomsApi {
    private val supabase = Supabase.getInstance()
    private val classroomsTable = supabase.from("classrooms")
    private val classroomUserTable = supabase.from("classroom_user")

    suspend fun getClassrooms(): List<Classroom> {
        val classrooms = classroomsTable.select().decodeList<Classroom>()
        return classrooms
    }

    suspend fun getClassroom(identifier: String, useClassCode: Boolean = false): Classroom?{
        val column = if (useClassCode) "code" else "id"
        val classroom = classroomsTable.select{
            filter {
                eq(column = column, value = identifier)
            }
            limit(1)
        }.decodeList<Classroom>()
        return try{
            classroom[0]
        }catch(e: Exception){
            null
        }

    }

    suspend fun insertClassroom(classroom: Classroom): Classroom {
        Log.i("MYTAG", "insertClassroom: $classroom")
        val newClassroom = classroomsTable.insert(classroom){
            select()
        }.decodeSingle<Classroom>()
        return newClassroom
    }

    suspend fun updateClassroom(classroom: Classroom): Classroom {
        val updatedClassroom = classroomsTable.update(classroom) {
            select()
            filter {
                eq("id", classroom.id)
            }
        }.decodeSingle<Classroom>()
        return updatedClassroom
    }

    suspend fun deleteClassroom(id: String): Classroom? {
        val deletedClassroom = classroomsTable.delete {
            select()
            filter {
                eq("id", id)
            }
        }.decodeList<Classroom>()
        return try{
            deletedClassroom[0]
        }catch(e: Exception){
            null
        }
    }

    suspend fun registerUser(userId: String, classCode: String): Classroom? {
        val classroomUser = ClassroomUser(
            user_id = userId,
            classroom_code = classCode
        )
        classroomUserTable.insert(classroomUser)
        val classroom = getClassroom(classCode, true)
        return classroom
    }

    suspend fun unRegisterUser(userId: String, classCode: String): Classroom? {
        classroomUserTable.delete{
            select()
            filter {
                and {
                    eq("classroom_code", classCode)
                    eq("user_id", userId)
                }
            }
        }
        val classroom = getClassroom(classCode, true)
        return classroom
    }
}