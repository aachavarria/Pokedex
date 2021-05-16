package com.example.pokedex.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Integer,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "email") val email: String
)
//@Entity(tableName = "users")
//class User {
//    @PrimaryKey(autoGenerate = true)
//    var id: Int? = null
//
//    @ColumnInfo(name = "userId")
//    var userId: String? = null
//}