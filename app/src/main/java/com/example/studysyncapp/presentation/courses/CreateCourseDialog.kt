package com.example.studysyncapp.presentation.courses

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
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.FormColorField
import com.example.studysyncapp.presentation.utils.FormColumn
import com.example.studysyncapp.presentation.utils.FormTextArea
import com.example.studysyncapp.presentation.utils.FormTextField
import com.example.studysyncapp.presentation.utils.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.DarkBlue
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.toColor
import com.example.studysyncapp.utils.toHexString


@Composable
fun CreateCourseDialog(onDismiss: () -> Unit, onConfirm: (name:String, color:String, description:String) -> Unit){

        var name by remember { mutableStateOf("") }
        var colorString by remember { mutableStateOf(DarkBlue.toHexString()) }
        var description by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        usePlatformDefaultWidth = false,
    )) {

        Log.d("MYTAG", "name: $name color: $colorString description: $description")

        Surface(modifier = Modifier.fillMaxSize().background(color = OffWhite).padding(UiVariables.ScreenPadding)) {
            Column(modifier = Modifier.fillMaxWidth().background(color = OffWhite), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.clickable(indication = null, interactionSource = null) { onDismiss() }){
                        Icon(painter = painterResource(R.drawable.ic_left_arrow), contentDescription = "Go Back", tint = AccentBlue, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Heading4("Add Course")
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(24.dp))
                }
                FormColumn {
                    FormTextField(value = name, label = "Course Title", onValueChange = {name = it})
                    FormColorField(
                        color = colorString.toColor(),
                        label = "Course Color",
                        onValueChange = {colorString = it})
                    FormTextArea(value = description, label = "Course Description", onValueChange = {description = it})
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
                DefaultButton(text = "Done", onClick = {onConfirm(name, colorString, description)}, enabled = name.isNotBlank())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateCourseDialogPreview(){
    CreateCourseDialog({}, onConfirm = {name, color, description -> {}})
}