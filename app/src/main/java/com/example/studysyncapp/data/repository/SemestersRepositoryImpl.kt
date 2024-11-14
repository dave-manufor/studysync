package com.example.studysyncapp.data.repository

import arrow.core.Either
import com.example.studysyncapp.data.mapper.toAppError
import com.example.studysyncapp.data.remote.SemesterApi
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Semester
import com.example.studysyncapp.domain.repository.SemestersRepository

class SemestersRepositoryImpl: SemestersRepository {
    private val semestersApi = SemesterApi()

    override suspend fun getSemesters(): Either<AppError, List<Semester>> {
        return Either.catch {
            semestersApi.getSemesters()
        }.mapLeft { it.toAppError() }
    }

    override suspend fun insertSemester(semester: Semester): Either<AppError, Semester> {
        return Either.catch {
            semestersApi.insertSemester(semester)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun updateSemester(semester: Semester): Either<AppError, Semester> {
        return Either.catch {
            semestersApi.updateSemester(semester)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun deleteSemester(semester: Semester): Either<AppError, Semester> {
        return Either.catch {
            semestersApi.deleteSemester(semester)
        }.mapLeft { it.toAppError() }
    }

}