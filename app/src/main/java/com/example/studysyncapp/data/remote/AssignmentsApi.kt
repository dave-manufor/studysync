package com.example.studysyncapp.data.remote

import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.Assignment
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns


class AssignmentsApi {
    private val supabase = Supabase.getInstance()
    private val assignmentsTable = supabase.from("assignments")

    suspend fun getAssignments(): List<Assignment>{
        val assignments = assignmentsTable.select(Columns.raw("*, course:courses(*)")).decodeList<Assignment>()
        return assignments
    }

    suspend fun getAssignmentsByClassroomId(classroomId: String): List<Assignment>{
        val assignments = assignmentsTable.select(Columns.raw("*, course:courses(*)")){
            filter {
                eq("classroom_id", classroomId)
            }
        }.decodeList<Assignment>()
        return assignments
    }

    suspend fun insertAssignment(assignment: Assignment): Assignment{
        val newAssignment = assignmentsTable.insert(assignment){
            select(Columns.raw("*, course:courses(*)"))
        }.decodeSingle<Assignment>()
        return newAssignment

    }

    suspend fun updateAssignment(assignment: Assignment): Assignment{
        val updatedAssignment = assignmentsTable.update(assignment) {
            select(Columns.raw("*, course:courses(*)"))
            filter {
                eq("id", assignment.id)
            }
        }.decodeSingle<Assignment>()

        return updatedAssignment
    }

    suspend fun deleteAssignment(id: String): Assignment{
        val deletedAssignment = assignmentsTable.delete {
            select(Columns.raw("*, course:courses(*)"))
            filter {
                eq("id", id)
            }
        }.decodeSingle<Assignment>()

        return deletedAssignment
    }

}