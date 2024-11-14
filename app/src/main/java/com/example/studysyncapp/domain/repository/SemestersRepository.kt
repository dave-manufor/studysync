package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Semester

interface SemestersRepository {
    suspend fun getSemesters(): Either<AppError, List<Semester>>
    suspend fun insertSemester(semester: Semester): Either<AppError, Semester>
    suspend fun updateSemester(semester: Semester): Either<AppError, Semester>
    suspend fun deleteSemester(semester: Semester): Either<AppError, Semester>

}