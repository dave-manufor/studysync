package com.example.studysyncapp.presentation.assignments_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studysyncapp.R
import com.example.studysyncapp.presentation.ElevatedCard
import com.example.studysyncapp.presentation.ElevatedCardCircle
import com.example.studysyncapp.presentation.ElevatedCardText
import com.example.studysyncapp.presentation.ElevatedCardTitle
import com.example.studysyncapp.presentation.Heading1
import com.example.studysyncapp.presentation.courses_screen.CreateCourseDialog
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.NeutralLight
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.toColor
import com.example.studysyncapp.utils.toPrettyDateTimeFormat

@Composable
fun AssignmentsScreen(assignmentsViewModel: AssignmentsViewModel = viewModel()){
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val state by assignmentsViewModel.state.collectAsState()
    if(state.isLoading){
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }
    if(!state.error.isNullOrEmpty()){
        Log.e("MYTAG", "Assignments Screen: ${state.error}")
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }
    Column(modifier = Modifier.padding(UiVariables.ScreenPadding)) {
        AssignmentHeader { assignmentsViewModel.onOpenCreateAssignment() }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            items(state.assignments) { assignment ->
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

//    Create Course Dialog
    if(state.showCreateAssignmentDialog){
        CreateAssignmentDialog(onDismiss = { assignmentsViewModel.onDismissCreateAssignment() },
            onConfirm = { name: String, courseId: String, description: String, dueAt: String ->
                assignmentsViewModel.onConfirmCreateAssignment(
                    assignmentName = name,
                    assignmentDescription = description,
                    courseId = courseId,
                    dueAt = dueAt
                )
            },
            courses = state.courses
        )
    }
}

@Composable
fun AssignmentHeader(onNavigateToCreateAssignment: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Heading1(text = "Assignments")
            TextButton(onClick = onNavigateToCreateAssignment,) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Assignment",
                    tint = AccentBlue,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = NeutralLight)
    }
}

@Composable
fun AssignmentCard(id: String, title: String, description: String, dueAt: String, accentColor: Color, isClassroom: Boolean, onNavigateToAssignment: (String) -> Unit){
    ElevatedCard(onClick = {onNavigateToAssignment(id)}) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start), modifier = Modifier.wrapContentWidth()) {
                ElevatedCardCircle(color = accentColor)
                ElevatedCardText(text = dueAt)
            }
            Box(modifier = Modifier.size(18.dp)){
                if(isClassroom){
                    Icon(painter = painterResource(R.drawable.ic_classroom), "Classroom Icon", tint = NeutralLight, modifier = Modifier.size(18.dp))
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top), horizontalAlignment = Alignment.Start) {
            ElevatedCardTitle(text = title)
            ElevatedCardText(text = description, modifier = Modifier.fillMaxWidth())
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssignmentsScreenPreview(){
    AssignmentsScreen()
}

//@Preview(showBackground = true)
//@Composable
//fun AssignmentCardPreview(){
//    AssignmentCard("", "Create Research Paper", "Write a research paper on a topic of your choice", "2024-11-27 10:00:00+00", Color.Red, false, {})
//}