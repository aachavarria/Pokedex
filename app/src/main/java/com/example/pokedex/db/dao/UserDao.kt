package com.example.pokedex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex.db.entities.User
import io.reactivex.Observable

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    fun getUser(email: String, password: String): Observable<User>

    @Insert
    fun registerUser(user: User)
}