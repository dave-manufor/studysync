package com.example.studysyncapp.presentation.courses_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studysyncapp.presentation.ElevatedCard
import com.example.studysyncapp.presentation.ElevatedCardCircle
import com.example.studysyncapp.presentation.ElevatedCardText
import com.example.studysyncapp.presentation.ElevatedCardTitle
import com.example.studysyncapp.presentation.Heading1
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.NeutralLight
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.toColor

@Composable
fun CoursesScreen(coursesViewModel: CoursesViewModel = viewModel()) {
    val context = LocalContext.current
    val state by coursesViewModel.state.collectAsState()
    if(state.isLoading){
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }
    if(!state.error.isNullOrEmpty()){
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
    }
    Column(modifier = Modifier.padding(UiVariables.ScreenPadding)) {
        CourseHeader { coursesViewModel.onOpenCreateCourse() }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            items(state.courses) { course ->
                CourseCard(
                    id = course.id,
                    title = course.name,
                    description = course.description ?: "",
                    accentColor = course.color.toColor(),
                    onNavigateToCourse = {}
                )
            }
        }
    }

//    Create Course Dialog
    if(state.showCreateCourseDialog){
        CreateCourseDialog(onDismiss = {coursesViewModel.onDismissCreateCourse()}, onConfirm = {name:String, color:String, description:String -> coursesViewModel.onConfirmCreateCourse(courseName = name, courseColor = color, courseDescription = description)})
    }
}

@Composable
fun CourseHeader(onNavigateToCreateCourse: () -> Unit){
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Heading1(text = "Courses")
            TextButton(onClick = onNavigateToCreateCourse,) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Course",
                    tint = AccentBlue,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = NeutralLight)
    }
}

@Composable
fun CourseCard(id: String, title: String, description: String, accentColor: Color, onNavigateToCourse: (String) -> Unit){
    ElevatedCard(onClick = {onNavigateToCourse(id)}) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start), modifier = Modifier.fillMaxWidth()) {
            ElevatedCardCircle(color = accentColor)
            ElevatedCardTitle(text = title, modifier = Modifier.weight(1f))
        }
        ElevatedCardText(text = description, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
private fun CoursesScreenPreview(){
    CoursesScreen()
}