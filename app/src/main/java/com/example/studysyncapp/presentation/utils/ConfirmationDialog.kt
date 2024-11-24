package com.example.studysyncapp.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studysyncapp.ui.theme.DarkBlue
import com.example.studysyncapp.ui.theme.NeutralDark
import com.example.studysyncapp.ui.theme.NeutralLight
import com.example.studysyncapp.ui.theme.UiVariables

enum class ConfirmationDialogType{
    NORMAL,
    DESTRUCTIVE
}

@Composable
fun ConfirmationDialog(title: String, body: String, onDismiss: () -> Unit, onConfirm: () -> Unit, cancelButtonText: String = "Cancel", confirmButtonText: String = "Confirm", type: ConfirmationDialogType = ConfirmationDialogType.NORMAL) {
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
                    Heading3(text = title)
                    IconButton(onClick = { onDismiss() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = NeutralLight)
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = NeutralLight)
                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
                Box(modifier = Modifier.fillMaxWidth().alpha(0.7f)){
                    BodyText(text = body)
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(0.5f)) {
                        DefaultButton(text = cancelButtonText, onClick = { onDismiss() }, textColor = NeutralDark, backgroundColor = NeutralLight)
                    }
                    Box(modifier = Modifier.weight(0.5f)) {
                        DefaultButton(text = confirmButtonText, onClick = { onConfirm() }, textColor = Color.White, backgroundColor = when(type){
                            ConfirmationDialogType.NORMAL -> DarkBlue
                            ConfirmationDialogType.DESTRUCTIVE -> Color.Red
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConfirmationDialogPreview() {
    ConfirmationDialog(title = "Leave Classroom", body = "Are you sure you want to leave this classroom? This action is permanent and cannot be undone.", onDismiss = {}, onConfirm = {}, type = ConfirmationDialogType.DESTRUCTIVE)
}