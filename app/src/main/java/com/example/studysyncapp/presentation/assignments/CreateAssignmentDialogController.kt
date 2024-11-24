package com.example.studysyncapp.presentation.assignments

interface CreateAssignmentDialogController {
    fun onOpenCreateAssignment()
    fun onDismissCreateAssignment()
    fun onConfirmCreateAssignment(assignmentName: String, assignmentDescription: String, courseId: String, dueAt: String, classroomId: String? = null)
}