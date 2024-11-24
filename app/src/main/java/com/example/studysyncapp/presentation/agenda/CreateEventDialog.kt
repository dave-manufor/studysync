package com.example.studysyncapp.presentation.agenda

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studysyncapp.R
import com.example.studysyncapp.domain.model.event.EventType
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.ErrorText
import com.example.studysyncapp.presentation.utils.FormColumn
import com.example.studysyncapp.presentation.utils.FormDateTimePicker
import com.example.studysyncapp.presentation.utils.FormDropdown
import com.example.studysyncapp.presentation.utils.FormTextArea
import com.example.studysyncapp.presentation.utils.FormTextField
import com.example.studysyncapp.presentation.utils.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.capitalizeWords
import com.example.studysyncapp.utils.toUTC
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun CreateEventDialog(onDismiss: () -> Unit, onConfirm: (name:String, type:EventType, description:String, startsAt: String, endsAt: String) -> Unit){
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(EventType.EVENT) }
    var description by remember { mutableStateOf("") }
    var startsAtDate by remember { mutableStateOf(LocalDate.now()) }
    var startsAtTime by remember { mutableStateOf(LocalTime.now()) }
    var endsAtDate by remember { mutableStateOf(LocalDate.now()) }
    var endsAtTime by remember { mutableStateOf(LocalTime.now().plusHours(1)) }
    val endsAtUTC by remember { derivedStateOf {
            LocalDateTime.of(endsAtDate, endsAtTime).toUTC()
        }
    }
    val startsAtUTC by remember { derivedStateOf {
            LocalDateTime.of(startsAtDate, startsAtTime).toUTC()
        }
    }
    val datesAreValid by remember {
        derivedStateOf {
            LocalDateTime.of(startsAtDate, startsAtTime).isBefore(LocalDateTime.of(endsAtDate, endsAtTime))
        }
    }

    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        usePlatformDefaultWidth = false,
    )
    ) {

        Surface(modifier = Modifier.fillMaxSize().background(color = OffWhite).padding(UiVariables.ScreenPadding)) {
            Column(modifier = Modifier.fillMaxWidth().background(color = OffWhite), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.clickable(indication = null, interactionSource = null) { onDismiss() }){
                        Icon(painter = painterResource(R.drawable.ic_left_arrow), contentDescription = "Go Back", tint = AccentBlue, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Heading4("Add Event")
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(24.dp))
                }
                FormColumn {
                    FormTextField(value = name, label = "Event Title", onValueChange = {name = it})
                    FormDropdown(options = EventType.entries, value = type, getValueText = ({it.toString().capitalizeWords()}), label = "Event Type", onValueChange = {type = it})
                    FormDateTimePicker(dateLabel = "Start Date", timeLabel = "Starts Time", date = startsAtDate, time = startsAtTime, onValueChange = {date, time -> startsAtDate = date; startsAtTime = time})
                    FormDateTimePicker(dateLabel = "End Date", timeLabel = "Ends Time", date = endsAtDate, time = endsAtTime, onValueChange = {date, time -> endsAtDate = date; endsAtTime = time})
                    FormTextArea(value = description, label = "Event Description", onValueChange = {description = it})
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
                DefaultButton(text = "Done", onClick = {onConfirm(name, type, description, startsAtUTC, endsAtUTC)}, enabled = name.isNotBlank() && datesAreValid)
                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                if(!datesAreValid){
                    ErrorText("Start date must be before end date")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventDialogPreview(){
    CreateEventDialog({}, {_,_,_,_,_ ->})

}