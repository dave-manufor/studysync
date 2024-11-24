package com.example.studysyncapp.presentation.classroom.list

interface CreateClassroomDialogController {
    fun onConfirmCreateClassroom(name:String, colorString:String, description:String?, inviteOnly: Boolean = false)
    fun onDismissCreateClassroom()
    fun onOpenCreateClassroom()
}