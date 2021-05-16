package com.example.pokedex.db.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex.db.entities.User

interface UserDao {
//    @Query("SELECT * FROM User")
    @Insert
    fun registerUser(user: User)
}