package com.example.studysyncapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studysyncapp.R
import com.example.studysyncapp.ui.theme.AccentBlue
import java.time.format.TextStyle

@Composable
fun Heading3(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight(800),
        color = Color.Black
    )
}

@Composable
fun BodyText(text: String){
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight(400),
        color = Color.Black,
        lineHeight = 16.sp
    )
}

@Composable
fun TitleDescriptionBar(title: String, description: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Heading3(text = title)
        BodyText(text = description)
    }
}

@Composable
fun FormTextField(value: String, label: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    OutlinedTextField(
        value = value,
        enabled = enabled,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
        shape = RoundedCornerShape(size = 12.dp),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun FormPasswordField(value: String, label: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        enabled = enabled,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
        shape = RoundedCornerShape(size = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                R.drawable.ic_visibility_on
            } else {
                R.drawable.ic_visibility_off
            }
            val description = if(passwordVisible) "Hide Password" else "Show Password"
            IconButton (onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(id = image), description)
            }
        }
    )
}

@Composable
fun DefaultButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(size = 12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
        modifier = modifier
            .fillMaxWidth()
    ){
        Text(text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = Color.White,
            )
    }
}

@Composable
fun LinkText(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick, contentPadding = PaddingValues(0.dp)) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = AccentBlue,
        )
    }
}