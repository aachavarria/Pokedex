package com.example.pokedex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex.db.dao.FavoriteDao
import com.example.pokedex.db.dao.UserDao
//import com.example.pokedex.db.entities.Favorite
import com.example.pokedex.db.entities.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?
    abstract fun favoriteDao(): FavoriteDao?

//    companion object {
//        private const val dbName = "pokedex_databse"
//        private var pokedexDatabase: PokedexDatabase? = null
//        @Synchronized
//        fun getPokedexDatabase(context: Context?): PokedexDatabase? {
//            if (pokedexDatabase == null) {
//                pokedexDatabase = Room.databaseBuilder(
//                    context!!,
//                    PokedexDatabase::class.java, dbName
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//            return pokedexDatabase
//        }
//    }
        companion object {
            @Volatile
            private var INSTANCE: PokedexDatabase? = null
            private const val dbName = "pokedex_databse"

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