package com.example.studysyncapp.presentation.courses_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CoursesScreen(onNavigateToCourse: (String) -> Unit, onNavigateToCreateCourse: () -> Unit){
    Text(text = "Courses Screen", fontSize = 32.sp)
}

@Preview(showBackground = true)
@Composable
private fun CoursesScreenPreview(){
    CoursesScreen({},{})
}