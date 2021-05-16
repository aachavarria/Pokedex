package com.example.pokedex.repositories

import android.content.Context
import androidx.room.Room
import com.example.pokedex.db.PokedexDatabase
import com.example.pokedex.db.entities.User

class PokedexRepository(context: Context) {
    var db: PokedexDatabase = Room.databaseBuilder(context, PokedexDatabase::class.java, "userDatabase")
        .build()
    fun insertUser(user: User) {
        db.userDao()?.registerUser(user)
    }
}