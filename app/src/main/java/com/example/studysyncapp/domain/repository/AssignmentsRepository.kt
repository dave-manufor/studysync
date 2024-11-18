package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Assignment

interface AssignmentsRepository {
    suspend fun getAssignments(): Either<AppError, List<Assignment>>
    suspend fun insertAssignment(assignment: Assignment): Either<AppError, Assignment>
    suspend fun updateAssignment(assignment: Assignment): Either<AppError, Assignment>
    suspend fun deleteAssignment(id: String): Either<AppError, Assignment>
}