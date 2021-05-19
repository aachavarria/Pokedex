package com.example.pokedex.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "trainerId") val trainerId: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "email") val email: String
)

