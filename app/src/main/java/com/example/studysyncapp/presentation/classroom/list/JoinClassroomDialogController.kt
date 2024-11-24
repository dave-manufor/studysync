package com.example.studysyncapp.presentation.classroom.list

interface JoinClassroomDialogController {
    fun onOpenJoinClassroom()
    fun onDismissJoinClassroom()
    fun onConfirmJoinClassroom(classCode: String)
}