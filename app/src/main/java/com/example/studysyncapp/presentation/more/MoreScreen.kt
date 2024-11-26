package com.example.studysyncapp.presentation.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.studysyncapp.core.auth.AuthViewModel
import com.example.studysyncapp.presentation.utils.ConfirmationDialog
import com.example.studysyncapp.presentation.utils.ConfirmationDialogType
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.ui.theme.UiVariables

@Composable
fun MoreScreen(authViewModel: AuthViewModel){
    val context = LocalContext.current
    var showConfirmDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(UiVariables.ScreenPadding)) {
        DefaultButton(text = "Logout", onClick = {showConfirmDialog = true}, backgroundColor = Color.Red)
        if(showConfirmDialog){
            ConfirmationDialog(
                title = "Logout Confirmation",
                body = "Are you sure you want to logout? Your data will be saved",
                onDismiss = {showConfirmDialog = false},
                onConfirm = {
                    showConfirmDialog = false
                    authViewModel.signOut(context)
                },
                cancelButtonText = "Cancel",
                confirmButtonText = "Logout",
                type = ConfirmationDialogType.DESTRUCTIVE
            )
        }
    }
}