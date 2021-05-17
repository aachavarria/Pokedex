package com.example.pokedex.repositories

import android.content.Context
import androidx.room.Room
import com.example.pokedex.db.PokedexDatabase
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User
import com.example.pokedex.models.Pokemon

class PokedexRepository(context: Context) {
    var db: PokedexDatabase = Room.databaseBuilder(context, PokedexDatabase::class.java, "userDatabase")
        .build()
    fun insertUser(user: User) {
        db.userDao()?.registerUser(user)
    }

    fun insertFavorite(favorite: Favorite) {
        db.favoriteDao()?.registerFavorite(favorite)
    }

    fun getFavorites(userId: Int) {
        db.favoriteDao()?.loadAllById(2)
    }
}