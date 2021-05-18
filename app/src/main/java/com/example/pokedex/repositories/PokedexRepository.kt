package com.example.pokedex.repositories

import android.content.Context
import com.example.pokedex.db.PokedexDatabase
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User
import io.reactivex.Observable

class PokedexRepository(context: Context) {

    private var db: PokedexDatabase = PokedexDatabase.getDatabase(context)

    suspend fun insertUser(user: User) {
        db.userDao().registerUser(user)
    }

    fun getUser(email: String, password: String): Observable<List<User>> {
        return db.userDao().getUser(email, password)
    }

    fun insertFavorite(favorite: Favorite) {
        db.favoriteDao().registerFavorite(favorite)
    }

    fun getFavorites(userId: Int): Observable<List<Favorite>> {
        return db.favoriteDao().loadAllById(userId)
    }

    fun removeFavorite(pokemonId: Int) {
        return db.favoriteDao().removeFavorite(pokemonId)
    }

    fun isFavorite(pokemonId: Int, userId: Int): Observable<Boolean> {
        return db.favoriteDao().isFavorite(pokemonId, userId).map { it.isNotEmpty() }
    }
}