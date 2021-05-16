package com.example.pokedex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex.db.dao.UserDao
import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User

@Database(entities = [User::class, Favorite::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?

    companion object {
        private const val dbName = "user"
        private var userDatabase: UserDatabase? = null
        @Synchronized
        fun getUserDatabase(context: Context?): UserDatabase? {
            if (userDatabase == null) {
                userDatabase = Room.databaseBuilder(
                    context!!,
                    UserDatabase::class.java, dbName
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return userDatabase

        }
    }
}
