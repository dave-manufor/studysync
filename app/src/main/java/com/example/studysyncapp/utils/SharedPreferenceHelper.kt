package com.example.studysyncapp.utils

import android.content.Context

class SharedPreferenceHelper(private val context: Context) {
    companion object{
        private const val MY_PREF_KEY = "STUDY_SYNC_APP"
    }

    fun saveStringData(key: String, value: String?){
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringData(key: String): String?{
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

}