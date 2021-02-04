package com.example.videogamesapplication.service

import androidx.room.*
import com.example.videogamesapplication.model.Games

@Dao
interface GamesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg game : Games) : List<Long>

    @Query("SELECT * FROM games ORDER BY id")
    suspend fun getAllGames() : List<Games>

    @Query("DELETE FROM games")
    suspend fun deleteAll()

    @Query("SELECT * FROM games where name like :searchQuery")
    suspend fun searchDatabase(searchQuery : String) : List<Games>

    @Query("SELECT * FROM games WHERE id = :id")
    suspend fun getGame(id: Int) : Games

    @Query("SELECT * FROM games WHERE isFavorited = 1")
    suspend fun getGameByFavorite() : List<Games>

    @Query("UPDATE games SET description = :description WHERE id = :id")
    suspend fun updateDescription(description : String, id: Int)

    @Query("UPDATE games SET isFavorited = 1 WHERE id = :id")
    suspend fun addFavorite(id: Int)

    @Query("UPDATE games SET isFavorited = 0 WHERE id = :id")
    suspend fun removeFavorite(id: Int)

    @Query("SELECT isFavorited FROM games WHERE id = :id")
    suspend fun isFavorited(id: Int) : Int


}