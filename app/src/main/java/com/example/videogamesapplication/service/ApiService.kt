package com.example.videogamesapplication.service

import com.example.videogamesapplication.constants.Constants
import com.example.videogamesapplication.model.game
import com.example.videogamesapplication.model.responseModel.gamelistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constants.getListofGames_URL)
    fun getListofGames() : Call<gamelistResponse>

    @GET(Constants.getListofGames_URL+"/{gameid}")
    fun getGameDetails(@Path("gameid") id : Int) : Call<game>

    }

