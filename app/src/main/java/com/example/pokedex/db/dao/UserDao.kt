package com.example.pokedex.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pokedex.db.entities.User
import com.example.pokedex.db.entities.UserFavorites

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>
    @Insert
    fun registerUser(user: User)

    @Transaction
    @Query("Select * FROM Users")
    fun getFavorites(): List<UserFavorites>
}