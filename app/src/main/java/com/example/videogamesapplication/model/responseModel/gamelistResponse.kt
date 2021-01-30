package com.example.videogamesapplication.model.responseModel

import com.example.videogamesapplication.model.game
import com.google.gson.annotations.SerializedName

data class gamelistResponse(

    @SerializedName("count")
    val count : Int,

    @SerializedName("next")
    val nextPageURL : String?,

    @SerializedName("previous")
    val previousPageURL : String?,

    @SerializedName("results")
    val results : List<game>

)
