package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.db.entities.User
import com.example.pokedex.repositories.PokedexRepository
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class CreateUserViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: PokedexRepository

   fun createUser(userId: String, password: String, email: String) {
        viewModelScope.launch {
            repository.insertUser(User(userId = userId, password = password, email = email))
        }
   }
}