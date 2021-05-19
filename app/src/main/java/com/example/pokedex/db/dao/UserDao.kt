package com.example.pokedex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedex.db.entities.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String): Observable<List<User>>

    @Query("UPDATE users SET trainerId = :trainerId , email = :email WHERE id = :id")
    fun updateUser(trainerId: String, email: String, id: Int): Completable

    @Insert
    fun registerUser(user: User)
}