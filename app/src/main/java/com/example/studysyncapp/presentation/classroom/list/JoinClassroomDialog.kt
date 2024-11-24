package com.example.studysyncapp.presentation.classroom.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.FormTextField
import com.example.studysyncapp.presentation.utils.Heading3
import com.example.studysyncapp.ui.theme.NeutralLight
import com.example.studysyncapp.ui.theme.UiVariables

@Composable
fun JoinClassroomDialog(onDismiss: () -> Unit, onConfirm: (classCode: String) -> Unit) {
    var classCode by remember { mutableStateOf("") }
    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        usePlatformDefaultWidth = false,
    )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = Color(0x0A000000)
                )
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(UiVariables.ScreenPadding)
        ) {
            Column(modifier = Modifier.background(color = Color.White)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Heading3(text = "Join Classroom")
                    IconButton(onClick = { onDismiss() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = NeutralLight)
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = NeutralLight)
                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
                FormTextField(value = classCode, label = "Class Code", onValueChange = { classCode = it })
                Spacer(modifier = Modifier.fillMaxWidth().height(12.dp))
                DefaultButton(text = "Join", onClick = { onConfirm(classCode) })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JoinClassroomDialogPreview() {
    JoinClassroomDialog(onDismiss = {}, onConfirm = {})
}