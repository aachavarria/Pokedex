package com.example.pokedex.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.models.Pokemon

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId")
    fun loadAllById(userId: Int): LiveData<List<Favorite>>

    @Insert
    fun registerFavorite(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}