package com.example.studysyncapp.core.auth

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studysyncapp.core.Supabase
import com.example.studysyncapp.utils.SharedPreferenceHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthViewModel(): ViewModel() {
    private val supabase = Supabase.getInstance()
    private var _authState = mutableStateOf<AuthState>(AuthState.Loading)
    val authState: State<AuthState> = _authState


    private fun saveToken(context: Context){
        viewModelScope.launch {
            val accessToken = supabase.auth.currentAccessTokenOrNull()
            Log.d("MYTAG", "saveToken: $accessToken")
            val sharedPref = SharedPreferenceHelper(context)
            sharedPref.saveStringData("accessToken", accessToken)
        }
    }

    private fun getToken(context: Context): String?{
        val sharedPref = SharedPreferenceHelper(context)
        val accessToken = sharedPref.getStringData("accessToken")
        Log.d("MYTAG", "getToken: $accessToken")
        return accessToken
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
                _authState.value = AuthState.Authenticated("Sign Up Successful")
            }catch (e: Exception){
                _authState.value = AuthState.Error("Sign Up Failed: ${e.message}")

            }
        }
    }

    fun signIn(context: Context, userEmail: String, userPassword: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _authState.value = AuthState.Authenticated("Login Successful")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Login Failed: ${e.message}")
            }
        }
    }

    fun signOut(context: Context){
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                val sharedPref = SharedPreferenceHelper(context)
                sharedPref.saveStringData("accessToken", null)
                _authState.value = AuthState.Unauthenticated("Sign Out Successful")
            }catch (e: Exception){
                _authState.value = AuthState.Error("Sign Out Failed: ${e.message}")
            }
        }
    }

    fun checkUserLoggedIn(context: Context){
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try{
                val token = getToken(context)
                Log.d("MYTAG", "checkUserLoggedIn: $token")
                if(token.isNullOrEmpty()){
                    _authState.value = AuthState.Unauthenticated("User not logged in")
                    Log.d("MYTAG", "checkUserLoggedIn: User not logged in")
                }else{
                    supabase.auth.retrieveUser(token)
                    supabase.auth.refreshCurrentSession()
                    Log.d("MYTAG", "checkUserLoggedIn: User logged in")
                    saveToken(context)
                    _authState.value = AuthState.Authenticated("User logged in")
                }
            }catch (e: Exception){
                Log.e("MYTAG", "checkUserLoggedIn: ${e.message}")
                _authState.value = AuthState.Error("Error: ${e.message}")
            }
        }
    }
}