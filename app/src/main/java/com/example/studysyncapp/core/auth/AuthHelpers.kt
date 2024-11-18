package com.example.studysyncapp.core.auth

import com.example.studysyncapp.core.Supabase
import io.github.jan.supabase.auth.auth

suspend fun getUserId(): String{
    val supabase = Supabase.getInstance()
    val user = supabase.auth.retrieveUserForCurrentSession(true)
    return user.id
}