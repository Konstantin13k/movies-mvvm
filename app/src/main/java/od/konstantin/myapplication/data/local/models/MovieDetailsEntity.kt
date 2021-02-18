package od.konstantin.myapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster_picture")
    val posterPicture: String,
    @ColumnInfo(name = "backdrop_picture")
    val backdropPicture: String,
    @ColumnInfo(name = "ratings")
    val ratings: Float,
    @ColumnInfo(name = "votes_count")
    val votesCount: Int,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "runtime")
    val runtime: Int,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
)