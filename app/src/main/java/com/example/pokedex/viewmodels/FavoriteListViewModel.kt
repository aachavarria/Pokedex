package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.models.Pokemon
import com.example.pokedex.repositories.PokedexRepository
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: PokedexRepository = PokedexRepository(application.applicationContext)

    fun favoriteList(userId: Int): Observable<List<Favorite>> {
        return repository.getFavorites(userId)
    }

    fun removeFavorite(pokemonId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(pokemonId)
        }
    }

    fun createFavorite(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(Favorite(userId = 1, pokemonId = pokemon.id, imageUrl = pokemon.imageUrl, name = pokemon.name, types = pokemon.types.joinToString().replace(" ", "")))
        }
    }

    fun isFavorite(pokemonId: Int, userId: Int): Observable<Boolean> {
        return repository.isFavorite(pokemonId, userId)
    }


}