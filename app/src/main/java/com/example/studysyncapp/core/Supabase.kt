package com.example.studysyncapp.core

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class Supabase {

    companion object{
        const val SUPABASE_URL = "https://umqproolpbmvshvlqidm.supabase.co"
        const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVtcXByb29scGJtdnNodmxxaWRtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzA5Njk5MjgsImV4cCI6MjA0NjU0NTkyOH0.toMYswwoY9WjLq4VHu_2QZn5IAVtMAiz8d6cqs92j4Q"

        @Volatile
        private var instance: SupabaseClient? = null

        fun getInstance(): SupabaseClient {
            return instance ?: synchronized(this){
                instance ?: createSupabaseClient(SUPABASE_URL, SUPABASE_KEY){
                    install(Postgrest)
                    install(Auth)
                }.also { instance = it }
            }
        }
    }
}