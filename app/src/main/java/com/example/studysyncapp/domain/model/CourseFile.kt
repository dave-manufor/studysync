package com.example.studysyncapp.domain.model

import com.example.studysyncapp.domain.model.file.File
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseFile (
    val course_id: String,
    val file_id: String,
    @SerialName("name")
    val path: String? = null,
    val path_tokens: List<String>? = null,
    val bucket_id: String? = null
)

fun CourseFile.toFile(): File{
    return File(id = file_id, path = path, path_tokens = path_tokens, bucket_id = bucket_id)
}