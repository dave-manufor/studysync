package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Classroom

interface ClassroomsRepository {
    suspend fun getClassrooms(): Either<AppError, List<Classroom>>
    suspend fun getClassroomById(id: String): Either<AppError, Classroom?>
    suspend fun getClassroomByCode(code: String): Either<AppError, Classroom?>
    suspend fun registerUser(userId: String, classCode: String): Either<AppError, Classroom?>
    suspend fun unRegisterUser(userId: String, classCode: String): Either<AppError, Classroom?>
    suspend fun insertClassroom(classroom: Classroom): Either<AppError, Classroom>
    suspend fun updateClassroom(classroom: Classroom): Either<AppError, Classroom>
    suspend fun deleteClassroom(id: String): Either<AppError, Classroom?>
}