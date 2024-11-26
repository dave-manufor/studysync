package com.example.studysyncapp.domain.repository

import arrow.core.Either
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.AssignmentFile
import com.example.studysyncapp.domain.model.CourseFile
import com.example.studysyncapp.domain.model.file.File
import com.example.studysyncapp.domain.model.file.FileType

interface FilesRepository {
    suspend fun uploadFile(
        fileName: String,
        byteArray: ByteArray,
        type: FileType,
        onProgressChange: (Float) -> Unit,
        onSuccess: (file: File) -> Unit,
        onError: (Exception) -> Unit,
        classroomId: String? = null
    )
    suspend fun attachFileToAssignment(assignmentId: String, fileId: String): Either<AppError, AssignmentFile>
    suspend fun attachFileToCourse(courseId: String, fileId: String): Either<AppError, CourseFile>
    suspend fun getAssignmentFiles(id: String): Either<AppError, List<File>>
    suspend fun getCourseFiles(id: String): Either<AppError, List<File>>
    suspend fun getClassroomFiles(id: String): Either<AppError, List<File>>
    suspend fun getSignedUrl(filePath: String): Either<AppError, String>
}