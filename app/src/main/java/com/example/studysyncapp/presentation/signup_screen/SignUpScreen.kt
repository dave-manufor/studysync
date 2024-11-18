package com.example.studysyncapp.presentation.signup_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studysyncapp.core.auth.AuthState
import com.example.studysyncapp.core.auth.AuthViewModel
import com.example.studysyncapp.presentation.BodyText
import com.example.studysyncapp.presentation.DefaultButton
import com.example.studysyncapp.presentation.FormColumn
import com.example.studysyncapp.presentation.FormPasswordField
import com.example.studysyncapp.presentation.FormRow
import com.example.studysyncapp.presentation.FormTextField
import com.example.studysyncapp.presentation.LinkText
import com.example.studysyncapp.presentation.TitleDescriptionBar
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.UiVariables

@Composable
fun SignUpScreen(onAuthenticated: () -> Unit, onNavigateToSignIn: () -> Unit, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val authState by authViewModel.authState

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errMsg by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }



    Surface(modifier = Modifier.fillMaxSize(), color = OffWhite) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(UiVariables.ScreenPadding).safeContentPadding(),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleDescriptionBar(
                title = "Sign Up",
                description = "Create an account to get started "
            )
            FormColumn {
                FormRow {
                    FormTextField(
                        value = firstName,
                        label = "First Name",
                        onValueChange = { firstName = it },
                        enabled = !isLoading,
                        modifier = Modifier.weight(1f)
                        )
                    FormTextField(
                        value = lastName,
                        label = "Last Name",
                        onValueChange = { lastName = it },
                        enabled = !isLoading,
                        modifier = Modifier.weight(1f)
                        )
                }
                FormTextField(
                    value = email,
                    label = "Email Address",
                    onValueChange = { email = it },
                    enabled = !isLoading)
                FormPasswordField(
                    value = password,
                    label = "Password",
                    onValueChange = { password = it },
                    enabled = !isLoading)
            }
            DefaultButton(
                text = "Create Account",
                onClick = { authViewModel.signUp(context, email, password, firstName, lastName) })
            if (errMsg.isNotEmpty()) {
                Text(
                    text = errMsg,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400)
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp, alignment = Alignment.CenterHorizontally)) {
                BodyText(text = "Already have an account?")
                LinkText(text = "Sign In", onClick = onNavigateToSignIn)
            }

            when (authState) {
                is AuthState.Error -> {
                    errMsg = (authState as AuthState.Error).message
                    isLoading = false
                }
                AuthState.Loading -> isLoading = true
                is AuthState.Authenticated -> onAuthenticated()
                is AuthState.Unauthenticated -> {
                    errMsg = (authState as AuthState.Unauthenticated).message
                    isLoading = false
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview(){
    SignUpScreen({},{})
}