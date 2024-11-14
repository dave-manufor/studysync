package com.example.studysyncapp.core.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.utils.SharedPreferenceHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthViewModel: ViewModel() {
    private val supabase = Supabase.getInstance()
    private var _authState = MutableStateFlow(AuthState.Loading)
    val authState = _authState.asStateFlow()

    private fun saveToken(context: Context){
        viewModelScope.launch {
            val accessToken = supabase.auth.currentAccessTokenOrNull()
            val sharedPref = SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken", accessToken)
        }
    }

    private fun getToken(context: Context): String?{
        val sharedPref = SharedPreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun signUp(context: Context, userEmail: String, userPassword: String, firstName: String, lastName: String){
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try{
                supabase.auth.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                    data = buildJsonObject {
                        put("first_name", firstName)
                        put("last_name", lastName)
                    }
                }
                saveToken(context)
            }catch (e: Exception){}
        }
    }
}