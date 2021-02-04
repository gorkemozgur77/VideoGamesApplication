package com.example.videogamesapplication.service

import com.example.videogamesapplication.util.Constants
import com.example.videogamesapplication.model.Games
import com.example.videogamesapplication.model.response.GamelistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constants.getListofGames_URL)
    fun getListofGames() : Call<GamelistResponse>

    @GET(Constants.getListofGames_URL+"/{gameid}")
    fun getGameDetails(@Path("gameid") id : Int) : Call<Games>

    }

