package com.example.studysyncapp.core.auth

sealed class AuthState {
    data class Authenticated(val message: String) : AuthState()
    data class Unauthenticated(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
    data object Loading : AuthState()

}