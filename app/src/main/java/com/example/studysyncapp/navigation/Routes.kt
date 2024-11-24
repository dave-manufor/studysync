package com.example.studysyncapp.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    @SerialName("sign-in")
    data object SignIn: Routes()

    @Serializable
    @SerialName("sign-up")
    data object SignUp: Routes()

    @Serializable
    @SerialName("splash")
    data object Splash: Routes()

    @Serializable
    @SerialName("courses")
    data object Courses: Routes()

    @Serializable
    @SerialName("agenda")
    data object Agenda: Routes()

    @Serializable
    @SerialName("assignments")
    data object Assignments: Routes()

    @Serializable
    @SerialName("classrooms")
    data object Classrooms: Routes()

    @Serializable
    @SerialName("classrooms/details")
    data class ClassroomDetails(val id: String): Routes()

    @Serializable
    @SerialName("more")
    data object More: Routes()
}