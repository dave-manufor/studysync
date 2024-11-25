package com.example.studysyncapp.presentation.classroom.details

import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.file.File

interface UploadFileDialogController {
    fun onDismissUploadFile()
    fun onOpenUploadFile()
    fun onConfirmUploadFile(isAssignment:Boolean, course:Course?, assignment:Assignment?, file: File)

}