package com.example.studysyncapp.presentation.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studysyncapp.R
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.FormColumn
import com.example.studysyncapp.presentation.utils.FormDropdown
import com.example.studysyncapp.presentation.utils.FormRow
import com.example.studysyncapp.presentation.utils.FormTextField
import com.example.studysyncapp.presentation.utils.FormTimePicker
import com.example.studysyncapp.presentation.utils.FormTwoWayToggle
import com.example.studysyncapp.presentation.utils.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.DaysOfTheWeek
import com.example.studysyncapp.utils.capitalizeWords
import java.time.LocalTime

@Composable
fun CreateScheduleDialog(courses: List<Course>, onDismiss:() -> Unit, onConfirm: (course: Course, dayOfTheWeek: DaysOfTheWeek, startTime: LocalTime, endTime: LocalTime, online: Boolean, location: String) -> Unit){
    var selectedCourse by remember { mutableStateOf(courses.first()) }
    var dayOfTheWeek by remember { mutableStateOf(DaysOfTheWeek.MONDAY) }
    var startTime by remember { mutableStateOf(LocalTime.now()) }
    var endTime by remember { mutableStateOf(LocalTime.now()) }
    var online by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        usePlatformDefaultWidth = false,
    )
    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite)
            .padding(UiVariables.ScreenPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = OffWhite),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = null
                        ) { onDismiss() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_left_arrow),
                            contentDescription = "Go Back",
                            tint = AccentBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Heading4("Add Schedule")
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(24.dp))
                }

                FormColumn {
                    FormDropdown(
                        options = courses,
                        value = selectedCourse,
                        getValueText = { it.name },
                        label = "Course",
                        onValueChange = { selectedCourse = it },
                    )
                    FormDropdown(
                        options = DaysOfTheWeek.entries,
                        value = dayOfTheWeek,
                        getValueText = { it.toString().capitalizeWords() },
                        label = "Day",
                        onValueChange = {it -> dayOfTheWeek = it},
                    )
                    FormRow {
                        FormTimePicker(time = startTime, label = "Starts At", onValueChange = {startTime = it}, modifier = Modifier.fillMaxWidth().weight(0.5f))
                        FormTimePicker(time = endTime, label = "Ends At", onValueChange = {endTime = it}, modifier = Modifier.fillMaxWidth().weight(0.5f))
                    }
                    FormTwoWayToggle(trueLabel = "Online", falseLabel = "Physical", value = online, onValueChange = {online = it})
                    FormTextField(value = location, label = if(online) "Link" else "Location", onValueChange = {location = it})
                    DefaultButton(text = "Confirm", onClick = {onConfirm(selectedCourse, dayOfTheWeek, startTime, endTime, online, location)})
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun CreateScheduleDialogPreview(){
    CreateScheduleDialog(listOf(), {}, {_,_,_,_,_,_ ->})
}