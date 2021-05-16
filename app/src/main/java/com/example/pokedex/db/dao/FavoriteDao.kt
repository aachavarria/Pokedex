package com.example.pokedex.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User

interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE user_id = :userId")
    fun loadAllById(userId: Int): List<Favorite>

    @Insert
    fun registerFavorite(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}