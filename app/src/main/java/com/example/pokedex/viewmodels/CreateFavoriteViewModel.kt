package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.models.Pokemon
import com.example.pokedex.repositories.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: PokedexRepository = PokedexRepository(application.applicationContext)

   fun createFavorite(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(Favorite(userId = 1, pokemonId = pokemon.id, imageUrl = pokemon.imageUrl, name = pokemon.name, types = pokemon.types.joinToString().replace(" ", "")))
        }
   }
}