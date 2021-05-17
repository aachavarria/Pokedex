package com.example.pokedex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex.db.dao.FavoriteDao
import com.example.pokedex.db.dao.UserDao
import com.example.pokedex.db.entities.Favorite
//import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User

@Database(entities = [User::class, Favorite::class], version = 1, exportSchema = false)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?
    abstract fun favoriteDao(): FavoriteDao?

    companion object {
        @Volatile
        private var INSTANCE: PokedexDatabase? = null
        private const val dbName = "pokedex_database"

        fun getDatabase(context: Context): PokedexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokedexDatabase::class.java, dbName
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}