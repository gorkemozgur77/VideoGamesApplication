package com.example.videogamesapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.videogamesapplication.model.Games
import com.example.videogamesapplication.model.response.GamelistResponse
import com.example.videogamesapplication.service.Client
import com.example.videogamesapplication.service.GameDatabase
import com.example.videogamesapplication.util.Constants
import com.example.videogamesapplication.util.TimePreferenceManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executors

class GamelistViewModel(application: Application) : BaseViewModel(application){

    val games = MutableLiveData<List<Games>>()
    val searchedGames = MutableLiveData<List<Games>>()

    fun getData(){
        val time =TimePreferenceManager(getApplication()).fetchTime()
        if(time !=null && time != 0L && System.nanoTime() - time < Constants.UPDATE_TIME)
            getFromDatabase()

        else
            requestAllgames()
    }

    private fun requestAllgames(){
        Client().getApiService().getListofGames().enqueue(object : retrofit2.Callback<GamelistResponse>{
            override fun onResponse(call: Call<GamelistResponse>, response: Response<GamelistResponse>) {
                println(response.body()?.results)
                if(response.isSuccessful){
                    response.body()?.results?.let {
                        saveToDatabase(it)
                        println("New data is saved into database from internet")
                    }
                }
            }
            override fun onFailure(call: Call<GamelistResponse>, t: Throwable) {
                println(t.cause)
                println(t.message)
                println(t.stackTrace)
            }
        })
    }

     fun searchData(query: String){
         Executors.newSingleThreadExecutor().execute {
             launch {
                 val dao = GameDatabase(getApplication()).gameDao()
                 searchedGames.value = dao.searchDatabase(query)
             }
         }
    }

    private fun saveToDatabase(gamelist : List<Games>){
        launch {
            val dao = GameDatabase(getApplication()).gameDao()
            dao.insertAll(*gamelist.toTypedArray())
            getFromDatabase()
        }
        TimePreferenceManager(getApplication()).saveTime(System.nanoTime())
    }

    private fun getFromDatabase(){
        launch {
            val gamelist = GameDatabase(getApplication()).gameDao().getAllGames()
            showGames(gamelist)
        }
        println("Recieved from Database")
    }

    private fun showGames(gamelist : List<Games>){
        games.value = gamelist
    }

}