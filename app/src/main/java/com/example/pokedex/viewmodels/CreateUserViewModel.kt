package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.db.entities.User
import com.example.pokedex.repositories.PokedexRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateUserViewModel(application: Application) : AndroidViewModel(application) {
    val repository = PokedexRepository(application.applicationContext)

   fun createUser(userId: String, password: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(User(userId = userId, password = password, email = email))
        }
   }
}