package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User
import com.example.pokedex.models.Pokemon
import com.example.pokedex.repositories.PokedexRepository
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: PokedexRepository

   fun favoriteList(userId: Int) {
        viewModelScope.launch {
            repository.getFavorites(userId)
        }
   }
}