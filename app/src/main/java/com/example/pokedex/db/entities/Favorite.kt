package com.example.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "favorites",
  foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"]
        )
    ])
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "pokemonId") val pokemonId: Int,
)
data class UserFavorites(
    @Embedded var user: User,
    @Relation(
        parentColumn = "id",
        entity = Favorite::class,
        entityColumn = "userId"
    )
    val favorites: List<Favorite>
)