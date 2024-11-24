package com.example.studysyncapp.presentation.courses

interface CreateCourseDialogController {
    fun onConfirmCreateCourse(courseName: String, courseColor: String, courseDescription: String, classroomId: String? = null)
    fun onOpenCreateCourse()
    fun onDismissCreateCourse()
}