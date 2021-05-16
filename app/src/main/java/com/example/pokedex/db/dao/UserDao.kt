package com.example.pokedex.db.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pokedex.db.entities.User
import com.example.pokedex.db.entities.UserFavorites

interface UserDao {
//    @Query("SELECT * FROM User")
    @Insert
    fun registerUser(user: User)

    @Transaction
    @Query("Select * FROM Users")
    fun getFavorites(): List<UserFavorites>
}