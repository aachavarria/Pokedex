package com.example.pokedex.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pokedex.db.PokedexDatabase
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User

class PokedexRepository(context: Context) {

    private var db: PokedexDatabase = PokedexDatabase.getDatabase(context)

    suspend fun insertUser(user: User) {
        db.userDao()?.registerUser(user)
    }

    suspend fun getUser(email: String, password: String) : User? {
        return db.userDao()?.getUser(email, password)
    }

    fun insertFavorite(favorite: Favorite) {
        db.favoriteDao().registerFavorite(favorite)
    }

    fun getFavorites(userId: Int) : LiveData<List<Favorite>> {
       return db.favoriteDao().loadAllById(2)
    }

}