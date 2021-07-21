package od.konstantin.myapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_actors",
    foreignKeys = [ForeignKey(
        entity = MovieDetailsEntity::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = CASCADE,
    )]
)
data class MovieActorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "actor_id")
    val actorId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "picture")
    val picture: String,
    @ColumnInfo(name = "movie_id", index = true)
    val movieId: Int,
    @ColumnInfo(name = "movie_rating")
    val movieRating: Int,
)