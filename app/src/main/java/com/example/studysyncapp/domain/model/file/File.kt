package com.example.studysyncapp.domain.model.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class File(
    val id: String,
    @SerialName("name")
    val path: String? = null,
    val path_tokens: List<String>? = null,
    val bucket_id: String? = null
)

fun File.getName(): String?{
    path_tokens?.let {
        return it.last()
    }
    return path?.split("/")?.last()
}
