package com.example.studysyncapp.data.remote

import android.util.Log
import com.example.studysyncapp.core.FileType
import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.core.auth.getUserId
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.uploadAsFlow

class FilesApi {
    private val bucket = Supabase.getInstance().storage.from("general-bucket")

    suspend fun uploadFile(
        fileName: String,
        byteArray: ByteArray,
        type: FileType,
        onProgressChange: (Float) -> Unit,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit,
        classroomId: String? = null
    ){
        val topDirectory = classroomId ?: getUserId()
        val subDirectory = type.value
        val path = "$topDirectory/$subDirectory/$fileName"
        try {
            bucket.uploadAsFlow(path, byteArray).collect {
                when (it) {
                    is UploadStatus.Progress -> onProgressChange(it.totalBytesSend.toFloat() / it.contentLength * 100)
                    is UploadStatus.Success -> {
                        onSuccess()
                        Log.d("MYTAG", "Upload File: Path = ${it.response.path}")
                        Log.d("MYTAG", "Upload File: Key = ${it.response.key}")
                    }
                }
            }
        }catch (e: Exception){
            onError(e)
        }
    }
}