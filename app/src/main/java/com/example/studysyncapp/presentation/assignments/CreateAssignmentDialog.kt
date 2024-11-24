package com.example.studysyncapp.presentation.assignments

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studysyncapp.R
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.FormColumn
import com.example.studysyncapp.presentation.utils.FormDatePicker
import com.example.studysyncapp.presentation.utils.FormDropdown
import com.example.studysyncapp.presentation.utils.FormTextArea
import com.example.studysyncapp.presentation.utils.FormTextField
import com.example.studysyncapp.presentation.utils.FormTimePicker
import com.example.studysyncapp.presentation.utils.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.toUTC
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Composable
fun CreateAssignmentDialog(courses: List<Course>, onDismiss: () -> Unit, onConfirm: (name:String, courseId: String, description:String, dueAt: String) -> Unit){

    var name by remember { mutableStateOf("") }
    var course by remember { mutableStateOf(courses.first()) }
    var date by remember { mutableStateOf(LocalDate.now())}
    var time by remember { mutableStateOf(LocalTime.now()) }
    val formattedUTCDate by remember {
        derivedStateOf {
            LocalDateTime.of(date, time).toUTC()
        }
    }
    var description by remember { mutableStateOf("") }

    Log.d("MYTAG", "Create Assignment Dialog: $date $time")
    Log.d("MYTAG", "Create Assignment Dialog: UTC - $formattedUTCDate")

    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        usePlatformDefaultWidth = false,
    )) {

        Surface(modifier = Modifier.fillMaxSize().background(color = OffWhite).padding(UiVariables.ScreenPadding)) {
            Column(modifier = Modifier.fillMaxWidth().background(color = OffWhite), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.clickable(indication = null, interactionSource = null) { onDismiss() }){
                        Icon(painter = painterResource(R.drawable.ic_left_arrow), contentDescription = "Go Back", tint = AccentBlue, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Heading4("Add Assignment")
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(24.dp))
                }
                FormColumn {
                    FormTextField(value = name, label = "Assignment Title", onValueChange = {name = it})
                    FormDropdown(options = courses, value = course, getValueText = ({it.name}), label = "Course", onValueChange = {course = it})
                    FormDatePicker(date = date, label = "Due Date", onValueChange = {date = it})
                    FormTimePicker(time = time, label = "Due Time", onValueChange = {time = it})
                    FormTextArea(value = description, label = "Assignment Description", onValueChange = {description = it})
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
                DefaultButton(text = "Done", onClick = {onConfirm(name, course.id, description, formattedUTCDate)}, enabled = (
                        name.isNotBlank() &&
                                course.id.isNotBlank()
                        ))
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CreateAssignmentDialogPreview(){
//    CreateAssignmentDialog({}, onConfirm = {name, color, description -> {}})
//}