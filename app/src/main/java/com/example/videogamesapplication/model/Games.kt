package com.example.videogamesapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Games(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val gameid : Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val gamename : String,

    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    val gamerating : Double,

    @ColumnInfo(name = "released")
    @SerializedName("released")
    val gamerelaseDate : String,

    @ColumnInfo(name = "background_image")
    @SerializedName("background_image")
    val gamePicture : String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description : String?,

    @ColumnInfo(name = "metacritic")
    @SerializedName("metacritic")
    val metacritic : Int,

    @ColumnInfo(name = "isFavorited")
    val isFavorited : Int
)
