package com.example.videogamesapplication.model.response

import com.example.videogamesapplication.model.Games
import com.google.gson.annotations.SerializedName

data class GamelistResponse(

    @SerializedName("count")
    val count : Int,

    @SerializedName("next")
    val nextPageURL : String?,

    @SerializedName("previous")
    val previousPageURL : String?,

    @SerializedName("results")
    val results : List<Games>

)
