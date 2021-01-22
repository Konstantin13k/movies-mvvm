package od.konstantin.myapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_posters")
data class MoviePosterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPicture: String,
    @ColumnInfo(name = "vote_average")
    val ratings: Float,
    @ColumnInfo(name = "vote_count")
    val votesCount: Int,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "adult")
    val adult: Boolean
)