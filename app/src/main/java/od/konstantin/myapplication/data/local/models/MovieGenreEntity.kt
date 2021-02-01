package od.konstantin.myapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_genres",
    foreignKeys = [ForeignKey(
        entity = MovieDetailsEntity::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = ForeignKey.CASCADE,
    )]
)
class MovieGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "genre_id")
    var genreId: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "movie_id")
    var movieId: Int,
)