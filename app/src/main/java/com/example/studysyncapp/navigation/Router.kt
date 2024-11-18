package com.example.studysyncapp.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.studysyncapp.core.auth.AuthState
import com.example.studysyncapp.core.auth.AuthViewModel
import com.example.studysyncapp.presentation.NavBar
import com.example.studysyncapp.presentation.agenda_screen.AgendaScreen
import com.example.studysyncapp.presentation.assignments_screen.AssignmentsScreen
import com.example.studysyncapp.presentation.courses_screen.CoursesScreen
import com.example.studysyncapp.presentation.courses_screen.CreateCourseDialog
import com.example.studysyncapp.presentation.load_screen.LoadScreen
import com.example.studysyncapp.presentation.signin_screen.SignInScreen
import com.example.studysyncapp.presentation.signup_screen.SignUpScreen
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.DarkBlue
import com.example.studysyncapp.ui.theme.UiVariables
import io.ktor.util.reflect.instanceOf

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

    val DEFAULT_SCREEN = Routes.Agenda
    val startScreen: Routes = when(authState){
        AuthState.Loading -> Routes.Splash
        is AuthState.Authenticated -> DEFAULT_SCREEN
        is AuthState.Error -> Routes.SignIn
        is AuthState.Unauthenticated -> Routes.SignIn
    }

    if(authState.instanceOf(AuthState.Authenticated::class)){
        Scaffold(
            bottomBar = {NavBar(navController)},
        ){
            innerPadding ->
            NavHost(navController = navController, startDestination = startScreen, modifier = Modifier.padding(innerPadding)){
                composable<Routes.Agenda> { AgendaScreen() }
                composable<Routes.Courses> { CoursesScreen() }
                composable<Routes.Assignments> { AssignmentsScreen() }
            }
        }
    }else{
        NavHost(navController = navController, startDestination = startScreen){
            composable<Routes.SignIn> { SignInScreen(
                onAuthenticated = {navController.navigate(Routes.Courses)},
                onNavigateToSignUp = {navController.navigate(Routes.SignUp)},
                authViewModel
            )
            }
            composable<Routes.SignUp> { SignUpScreen(
                onAuthenticated = {navController.navigate(Routes.Courses)},
                onNavigateToSignIn = {navController.navigate(Routes.SignIn)},
                authViewModel
            )
            }
            composable<Routes.Splash> { LoadScreen() }
        }
    }

}