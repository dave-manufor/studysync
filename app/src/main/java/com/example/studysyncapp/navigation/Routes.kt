package com.example.studysyncapp.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object SignIn: Routes()

    @Serializable
    data object SignUp: Routes()

    @Serializable
    data object Loading: Routes()

    @Serializable
    data object Courses: Routes()
}