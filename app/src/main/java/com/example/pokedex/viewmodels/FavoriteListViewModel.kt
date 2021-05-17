package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User
import com.example.pokedex.models.Pokemon
import com.example.pokedex.repositories.PokedexRepository
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: PokedexRepository = PokedexRepository(application.applicationContext)

    fun favoriteList(userId: Int): LiveData<List<Favorite>> {
        return repository.getFavorites(userId)
    }
}