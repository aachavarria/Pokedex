package com.example.pokedex.db.entities

import androidx.room.*

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"]
        )
    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Integer,
    @ColumnInfo(name = "user_id") val userId: Integer,
    @ColumnInfo(name = "pokemon_id") val pokemonId: Integer,
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