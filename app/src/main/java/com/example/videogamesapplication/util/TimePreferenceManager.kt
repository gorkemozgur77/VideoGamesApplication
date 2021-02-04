package com.example.videogamesapplication.util

import android.content.Context
import android.content.SharedPreferences

class TimePreferenceManager (context: Context) {



    private var prefs: SharedPreferences = context.getSharedPreferences("Time", Context.MODE_PRIVATE)

    companion object {
        const val TIME = "time"
    }


    fun saveTime(time: Long) {
        val editor = prefs.edit()
        editor.putLong(TIME, time)
        editor.apply()
    }


    fun fetchTime(): Long? { return prefs.getLong(TIME, 0) }

}