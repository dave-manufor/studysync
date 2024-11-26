package com.example.studysyncapp.data.remote

import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.AssignmentFile
import com.example.studysyncapp.domain.model.file.File
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns


class AssignmentsApi {
    private val supabase = Supabase.getInstance()
    private val assignmentsTable = supabase.from("assignments")
    private val assignmentFileTable = supabase.from("assignment_file")

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

    suspend fun attatchFileToAssignment(assignmentId: String, fileId: String): AssignmentFile {
        val assignmentFile = AssignmentFile(
            assignment_id = assignmentId,
            file_id = fileId
        )
        val attatchment = assignmentFileTable.insert(assignmentFile){
            select()
        }.decodeSingle<AssignmentFile>()
        return attatchment
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