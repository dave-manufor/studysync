package com.example.studysyncapp.data.repository

import arrow.core.Either
import com.example.studysyncapp.data.mapper.toAppError
import com.example.studysyncapp.data.remote.ClassroomsApi
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Classroom
import com.example.studysyncapp.domain.repository.ClassroomsRepository

class ClassroomsRepositoryImpl: ClassroomsRepository {
    private val classroomsApi = ClassroomsApi()

    override suspend fun getClassrooms(): Either<AppError, List<Classroom>> {
        return Either.catch{
            classroomsApi.getClassrooms()
        }.mapLeft{
            it.toAppError()
        }
    }

    override suspend fun getClassroomById(id: String): Either<AppError, Classroom?> {
        return Either.catch{
            classroomsApi.getClassroom(id)
        }.mapLeft{
            it.toAppError()
        }
    }

    override suspend fun getClassroomByCode(code: String): Either<AppError, Classroom?> {
        return Either.catch{
            classroomsApi.getClassroom(
                identifier = code,
                useClassCode = true
            )
        }.mapLeft{
            it.toAppError()
        }
    }

    override suspend fun registerUser(
        userId: String,
        classCode: String
    ): Either<AppError, Classroom?> {
        return Either.catch {
                 classroomsApi.registerUser(userId, classCode)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun unRegisterUser(
        userId: String,
        classCode: String
    ): Either<AppError, Classroom?> {
        return Either.catch {
                classroomsApi.unRegisterUser(userId, classCode)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun insertClassroom(classroom: Classroom): Either<AppError, Classroom> {
        return Either.catch {
            classroomsApi.insertClassroom(classroom)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun updateClassroom(classroom: Classroom): Either<AppError, Classroom> {
        return Either.catch {
            classroomsApi.updateClassroom(classroom)
        }.mapLeft {
            it.toAppError()
        }
    }

    override suspend fun deleteClassroom(id: String): Either<AppError, Classroom?> {
        return Either.catch {
            classroomsApi.deleteClassroom(id)
        }.mapLeft {
            it.toAppError()
        }
    }
}