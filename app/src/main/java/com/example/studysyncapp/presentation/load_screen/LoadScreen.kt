package com.example.studysyncapp.presentation.load_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.studysyncapp.R
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.UiVariables

@Composable
fun LoadScreen(){
    Surface(modifier = Modifier.fillMaxSize(), color = AccentBlue) {
        Column(modifier = Modifier.fillMaxSize().padding(UiVariables.ScreenPadding).safeContentPadding(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_splash),
                contentDescription = "Loader Icon"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadScreenPreview(){
    LoadScreen()
}