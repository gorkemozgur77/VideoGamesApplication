package com.example.videogamesapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.videogamesapplication.model.Games
import com.example.videogamesapplication.service.GameDatabase
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : BaseViewModel(application) {
    val gameList = MutableLiveData<List<Games>>()



    fun getGames(){
        launch {
            gameList.value = GameDatabase(getApplication()).gameDao().getGameByFavorite()
        }
    }

}