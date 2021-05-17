package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.repositories.PokedexRepository
import io.reactivex.Observable

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: PokedexRepository = PokedexRepository(application.applicationContext)

    fun favoriteList(userId: Int): Observable<List<Favorite>> {
        return repository.getFavorites(userId)
    }
}