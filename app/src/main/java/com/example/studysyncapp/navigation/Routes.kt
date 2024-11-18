package com.example.studysyncapp.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object SignIn: Routes()

    @Serializable
    data object SignUp: Routes()

    @Serializable
    data object Splash: Routes()

    @Serializable
    data object Courses: Routes()

    @Serializable
    data object CreateCourse: Routes()

    @Serializable
    data object Agenda: Routes()

    @Serializable
    data object Assignments: Routes()

    @Serializable
    data object Classrooms: Routes()

    @Serializable
    data object More: Routes()
}