package com.example.studysyncapp.data.repository

import arrow.core.Either
import com.example.studysyncapp.data.mapper.toAppError
import com.example.studysyncapp.data.remote.FilesApi
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.AssignmentFile
import com.example.studysyncapp.domain.model.CourseFile
import com.example.studysyncapp.domain.model.file.File
import com.example.studysyncapp.domain.model.file.FileType
import com.example.studysyncapp.domain.repository.FilesRepository

class FilesRepositoryImpl: FilesRepository{
    private val filesApi = FilesApi()
    override suspend fun uploadFile(
        fileName: String,
        byteArray: ByteArray,
        type: FileType,
        onProgressChange: (Float) -> Unit,
        onSuccess: (file: File) -> Unit,
        onError: (Exception) -> Unit,
        classroomId: String?
    ) {
            filesApi.uploadFile(
                fileName = fileName,
                byteArray = byteArray,
                type = type,
                onProgressChange = onProgressChange,
                onSuccess = onSuccess,
                onError = onError,
                classroomId = classroomId
            )
    }

    override suspend fun attachFileToAssignment(
        assignmentId: String,
        fileId: String
    ): Either<AppError, AssignmentFile> {
        return Either.catch {
            filesApi.attachFileToAssignment(assignmentId, fileId)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun attachFileToCourse(
        courseId: String,
        fileId: String
    ): Either<AppError, CourseFile> {
        return Either.catch {
            filesApi.attachFileToCourse(courseId, fileId)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun getAssignmentFiles(id: String): Either<AppError, List<File>> {
        return Either.catch {
            filesApi.getAssignmentFiles(id)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun getCourseFiles(id: String): Either<AppError, List<File>> {
        return Either.catch {
            filesApi.getCourseFiles(id)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun getClassroomFiles(id: String): Either<AppError, List<File>> {
        return Either.catch {
            filesApi.getClassroomFiles(id)
        }.mapLeft { it.toAppError() }
    }

    override suspend fun getSignedUrl(filePath: String): Either<AppError, String> {
        return Either.catch {
            filesApi.getSignedUrl(filePath)
        }.mapLeft { it.toAppError() }
    }

}