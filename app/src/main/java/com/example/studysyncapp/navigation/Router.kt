package com.example.studysyncapp.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.studysyncapp.core.auth.AuthState
import com.example.studysyncapp.core.auth.AuthViewModel
import com.example.studysyncapp.presentation.utils.NavBar
import com.example.studysyncapp.presentation.agenda.AgendaScreen
import com.example.studysyncapp.presentation.assignments.AssignmentsScreen
import com.example.studysyncapp.presentation.classroom.details.ClassroomDetailsScreen
import com.example.studysyncapp.presentation.classroom.list.ClassroomsScreen
import com.example.studysyncapp.presentation.courses.CoursesScreen
import com.example.studysyncapp.presentation.load.LoadScreen
import com.example.studysyncapp.presentation.more.MoreScreen
import com.example.studysyncapp.presentation.signin.SignInScreen
import com.example.studysyncapp.presentation.signup.SignUpScreen
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
            bottomBar = { NavBar(navController) },
        ){
            innerPadding ->
            NavHost(navController = navController, startDestination = startScreen, modifier = Modifier.padding(innerPadding)){
                composable<Routes.Agenda> { AgendaScreen() }
                composable<Routes.Courses> { CoursesScreen() }
                composable<Routes.Assignments> { AssignmentsScreen() }
                composable<Routes.Classrooms> { ClassroomsScreen(onNavigateToClassroom = {
                    navController.navigate(route = Routes.ClassroomDetails(id = it))
                }) }
                composable<Routes.ClassroomDetails> { backStackEntry ->
                    val args = backStackEntry.toRoute<Routes.ClassroomDetails>()
                    ClassroomDetailsScreen(id = args.id, onNavigateToClassroomsList = {
                        navController.navigate(route = Routes.Classrooms)
                    })
                }
                composable<Routes.More> { MoreScreen(authViewModel) }
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