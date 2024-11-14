package com.example.studysyncapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.studysyncapp.navigation.Router
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.StudySyncAppTheme
import com.example.studysyncapp.ui.theme.UiVariables

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            StudySyncAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = OffWhite) {
                    Router()
                }
            }
        }
    }

}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(10) {

        Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    StudySyncAppTheme {
        Greeting("Android")
    }
}
