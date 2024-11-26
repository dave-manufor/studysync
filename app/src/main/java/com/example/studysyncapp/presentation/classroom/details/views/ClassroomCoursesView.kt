package com.example.studysyncapp.presentation.classroom.details.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.presentation.utils.ElevatedCard
import com.example.studysyncapp.presentation.utils.ElevatedCardCircle
import com.example.studysyncapp.presentation.utils.ElevatedCardText
import com.example.studysyncapp.presentation.utils.ElevatedCardTitle
import com.example.studysyncapp.utils.toColor

@Composable
fun ClassroomCoursesView(courses: List<Course>){
    val scrollState = rememberScrollState()
    Column(verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxHeight(1f).verticalScroll(scrollState)){
        courses.forEach {
            ElevatedCard{
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start), modifier = Modifier.fillMaxWidth()) {
                    ElevatedCardCircle(color = it.color.toColor())
                    ElevatedCardTitle(text = it.name, modifier = Modifier.weight(1f))
                }
                it.description?.let { description ->
                    ElevatedCardText(text = description, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
    }
}