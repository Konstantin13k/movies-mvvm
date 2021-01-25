package od.konstantin.myapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "favorite_movie_id")
    val movieId: Int
)