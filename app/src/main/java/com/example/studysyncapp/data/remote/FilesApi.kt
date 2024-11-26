package com.example.studysyncapp.data.remote

import android.util.Log
import com.example.studysyncapp.domain.model.file.FileType
import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.core.auth.getUserId
import com.example.studysyncapp.domain.model.AssignmentFile
import com.example.studysyncapp.domain.model.CourseFile
import com.example.studysyncapp.domain.model.file.File
import com.example.studysyncapp.domain.model.toFile
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.uploadAsFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class FilesApi {
    private val supabase = Supabase.getInstance()
    private val bucket = supabase.storage.from("general-bucket")
    private val assignmentFileTable = supabase.from("assignment_file")
    private val assignmentFileView = supabase.from("assignment_file_view")
    private val courseFileTable = supabase.from("course_file")
    private val courseFileView = supabase.from("course_file_view")

    suspend fun uploadFile(
        fileName: String,
        byteArray: ByteArray,
        type: FileType,
        onProgressChange: (Float) -> Unit,
        onSuccess: (file: File) -> Unit,
        onError: (Exception) -> Unit,
        classroomId: String? = null
    ){
        val topDirectory = classroomId ?: getUserId()
        val subDirectory = type.value
        val path = "$topDirectory/$subDirectory/$fileName"
        try {
            bucket.uploadAsFlow(path, byteArray).collect { it ->
                when (it) {
                    is UploadStatus.Progress -> onProgressChange(it.totalBytesSend.toFloat() / it.contentLength * 100)
                    is UploadStatus.Success -> {
                        onSuccess(File(id = it.response.id ?: "", path = it.response.path))
                        Log.d("MYTAG", "Upload File: Path = ${it.response.path}")
                        Log.d("MYTAG", "Upload File: Key = ${it.response.key}")
                    }
                }
            }
        }catch (e: Exception){
            onError(e)
        }
    }

    suspend fun attachFileToAssignment(assignmentId: String, fileId: String): AssignmentFile {
        val assignmentFile = AssignmentFile(
            assignment_id = assignmentId,
            file_id = fileId
        )
        val attachment = assignmentFileTable.insert(assignmentFile){
            select()
        }.decodeSingle<AssignmentFile>()
        return attachment
    }

    suspend fun attachFileToCourse(courseId: String, fileId: String): CourseFile {
        val courseFile = CourseFile(
            course_id = courseId,
            file_id = fileId
        )
        val attachment = courseFileTable.insert(courseFile){
            select()
        }.decodeSingle<CourseFile>()
        return attachment
    }

    suspend fun getAssignmentFiles(id: String): List<File> {
        val files = assignmentFileView.select(Columns.raw("*, file:storage.objects(*)")) {
            filter {
                eq("assignment_id", id)
            }
        }.decodeList<File>()
        return files
    }

    suspend fun getCourseFiles(id: String): List<File> {
        val files = courseFileView.select(Columns.raw("*, file:storage.objects(*)")){
            filter {
                eq("course_id", id)
            }
        }.decodeList<File>()
        return files
    }

    suspend fun getClassroomFiles(id: String): List<File> = coroutineScope {
        val deferredAssignmentFiles = async {
            assignmentFileView.select(Columns.raw(
                "*, assignments!inner(classroom_id)"
            )){
                filter {
                    eq("assignments.classroom_id", id)
                }
            }.decodeList<AssignmentFile>()
        }
        val deferredCourseFiles = async {
            courseFileView.select(Columns.raw(
                "*, courses!inner(classroom_id)"
            )){
                filter {
                    eq("courses.classroom_id", id)
                }
            }.decodeList<CourseFile>()
        }

        val assignmentFiles = deferredAssignmentFiles.await()
        val courseFiles = deferredCourseFiles.await()

        assignmentFiles.map { it.toFile() } + courseFiles.map { it.toFile() }
    }

    suspend fun getSignedUrl(filePath: String, expiresIn: Duration = 24.toDuration(DurationUnit.DAYS)): String {
        val url = bucket.createSignedUrl(filePath, expiresIn)
        return url
    }
}