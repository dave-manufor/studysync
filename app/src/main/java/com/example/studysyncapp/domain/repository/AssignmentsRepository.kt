package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.AssignmentFile
import com.example.studysyncapp.domain.model.Schedule
import com.example.studysyncapp.domain.model.file.File

interface AssignmentsRepository {
    suspend fun getAssignments(): Either<AppError, List<Assignment>>
    suspend fun getAssignmentsByClassroomId(id: String): Either<AppError, List<Assignment>>
    suspend fun attatchFileToAssignment(assignment: Assignment, file: File): Either<AppError, AssignmentFile>
    suspend fun insertAssignment(assignment: Assignment): Either<AppError, Assignment>
    suspend fun updateAssignment(assignment: Assignment): Either<AppError, Assignment>
    suspend fun deleteAssignment(id: String): Either<AppError, Assignment>
}