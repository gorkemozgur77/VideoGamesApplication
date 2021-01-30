package com.example.videogamesapplication.model

import com.google.gson.annotations.SerializedName

data class game(

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("description")
    val description : String,

    @SerializedName("rating")
    val rating : Double,

    @SerializedName("metacritic")
    val metascore : Int,

    @SerializedName("released")
    val relaseDate : String,

    @SerializedName("background_image")
    val gamePicture : String

)
