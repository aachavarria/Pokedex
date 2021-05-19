package com.example.pokedex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex.db.entities.Favorite
import io.reactivex.Observable

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId")
    fun loadAllById(userId: Int): Observable<List<Favorite>>

    @Insert
    fun registerFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE pokemonId = :pokemonId AND userId = :userId")
    fun removeFavorite(pokemonId: Int, userId: Int)

    @Query("SELECT * FROM favorites WHERE pokemonId = :pokemonId AND userId = :userId")
    fun isFavorite(pokemonId: Int, userId: Int): Observable<List<Favorite>>
}