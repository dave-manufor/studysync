package com.example.studysyncapp.data.repository

import arrow.core.Either
import com.example.studysyncapp.data.mapper.toAppError
import com.example.studysyncapp.data.remote.AssignmentsApi
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.repository.AssignmentsRepository

class AssignmentsRepositoryImpl: AssignmentsRepository {
    private val assignmentsApi = AssignmentsApi()

    override suspend fun getAssignments(): Either<AppError, List<Assignment>> {
        return Either.catch {
            assignmentsApi.getAssignments()
        }.mapLeft { it.toAppError() }
    }

    override suspend fun insertAssignment(assignment: Assignment): Either<AppError, Assignment> {
        return Either.catch {
            assignmentsApi.insertAssignment(assignment)
        }.mapLeft{it.toAppError()}
    }

    override suspend fun updateAssignment(assignment: Assignment): Either<AppError, Assignment> {
        return Either.catch {
            assignmentsApi.updateAssignment(assignment)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun deleteAssignment(id: String): Either<AppError, Assignment> {
        return Either.catch {
            assignmentsApi.deleteAssignment(id)
        }.mapLeft { it.toAppError() }
    }
}