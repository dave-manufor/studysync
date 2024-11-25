package com.example.studysyncapp.presentation.classroom.details.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.presentation.assignments.AssignmentCard
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.utils.toColor
import com.example.studysyncapp.utils.toPrettyDateTimeFormat

@Composable
fun ClassroomAssignmentsView(assignments: List<Assignment>){
    LazyColumn(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        items(assignments) { assignment ->
            Log.d("MYTAG", assignment.toString())
            AssignmentCard(
                id = assignment.id,
                title = assignment.name,
                description = assignment.description ?: "",
                dueAt = assignment.due_at.toPrettyDateTimeFormat(),
                accentColor = assignment.course?.color?.toColor() ?: AccentBlue,
                isClassroom = assignment.classroom_id != null,
                onNavigateToAssignment = {}
            )
        }
    }
}