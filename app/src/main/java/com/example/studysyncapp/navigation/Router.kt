package com.example.studysyncapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studysyncapp.core.auth.AuthState
import com.example.studysyncapp.core.auth.AuthViewModel
import com.example.studysyncapp.presentation.courses_screen.CoursesScreen
import com.example.studysyncapp.presentation.load_screen.LoadScreen
import com.example.studysyncapp.presentation.signin_screen.SignInScreen
import com.example.studysyncapp.presentation.signup_screen.SignUpScreen

@Composable
fun Router(){
    val context = LocalContext.current
    val navController = rememberNavController()
    val authViewModel = remember { AuthViewModel() }
    val authState by authViewModel.authState
    LaunchedEffect(Unit) {
        authViewModel.checkUserLoggedIn(context)
    }
    Log.d("MYTAG", "Router: $authState")
    val startScreen: Routes = when(authState){
        is AuthState.Authenticated -> Routes.Courses
        is AuthState.Error -> Routes.SignIn
        AuthState.Loading -> Routes.Loading
        is AuthState.Unauthenticated -> Routes.SignIn
    }

//    Navigation Host
    NavHost(navController = navController, startDestination = startScreen){
        composable<Routes.SignIn> { SignInScreen({},{},authViewModel) }
        composable<Routes.SignUp> { SignUpScreen({},{},authViewModel) }
        composable<Routes.Loading> { LoadScreen() }
        composable<Routes.Courses> { CoursesScreen({},{}) }
    }

}