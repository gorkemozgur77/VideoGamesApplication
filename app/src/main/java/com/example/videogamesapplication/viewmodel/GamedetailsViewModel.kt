package com.example.videogamesapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.videogamesapplication.model.Games
import com.example.videogamesapplication.service.Client
import com.example.videogamesapplication.service.GameDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class GamedetailsViewModel(application: Application) : BaseViewModel(application) {

    val detailedGame = MutableLiveData<Games>()
    val progressBarVisibility = MutableLiveData<Boolean>() // True visible, False Invisible
    val isFavorited = MutableLiveData<Int>()

    fun getDetails(id: Int){
        requestDetails(id)
    }

    private fun requestDetails(id : Int){

        progressBarVisibility.value = true
        Client().getApiService().getGameDetails(id).enqueue(object : retrofit2.Callback<Games>{
            override fun onResponse(call: Call<Games>, response: Response<Games>) {
                progressBarVisibility.value = false
                if (response.isSuccessful){
                    detailedGame.value = response.body()
                    updateDatabase(response.body()!!)
                }
            }
            override fun onFailure(call: Call<Games>, t: Throwable) {
                println(t.cause)
                println(t.message)
                println(t.stackTrace)
            }
        })
    }

    private fun updateDatabase(game : Games){
        launch {
            game.description?.let {
                GameDatabase(getApplication()).gameDao().updateDescription(it,game.gameid)
            }
        }
    }
     fun addFav(id: Int){
        launch {
            GameDatabase(getApplication()).gameDao().addFavorite(id)
        }
    }
    fun deleteFav(id: Int){
        launch {
            GameDatabase(getApplication()).gameDao().removeFavorite(id)
        }
    }
     fun isFavorited(id: Int){
        launch {
           isFavorited.value = GameDatabase(getApplication()).gameDao().isFavorited(id)
        }
    }
}