package com.example.videogamesapplication.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.videogamesapplication.model.Games

@Database(entities = [Games::class],version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao() : GamesDAO

    companion object {
        @Volatile private var instance : GameDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GameDatabase::class.java, "GamesDatabase").build()
    }



}