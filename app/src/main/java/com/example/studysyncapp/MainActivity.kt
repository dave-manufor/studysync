package com.example.studysyncapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.studysyncapp.data.remote.CoursesApi
import com.example.studysyncapp.data.repository.CoursesRepositoryImpl
import com.example.studysyncapp.domain.model.AppError
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.ui.theme.StudySyncAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
        val coursesApi = CoursesApi()
        val coursesRepository = CoursesRepositoryImpl(coursesApi)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        CoroutineScope(Dispatchers.IO).launch {
            var courses: Either<AppError, List<Course>>? = null
            withContext(Dispatchers.IO){
                courses = coursesRepository.getCourses()
            }

            courses?.fold({
                appError -> Log.i("MYTAG", appError.message)
            }, {
                courseList -> Log.i("MYTAG", courseList.toString())
            })
        }
        setContent {
            StudySyncAppTheme {
                Text(text = "Building Backend",
                    fontSize = 40.sp,
                )
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
