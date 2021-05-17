package com.example.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "favorites",
  foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"]
        )
    ]
)
data class Favorite(
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "pokemonId") val pokemonId: Int,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "types") val types: String,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
data class UserFavorites(
    @Embedded var user: User,
    @Relation(
        parentColumn = "id",
        entity = Favorite::class,
        entityColumn = "userId"
    )
    val favorites: List<Favorite>
)