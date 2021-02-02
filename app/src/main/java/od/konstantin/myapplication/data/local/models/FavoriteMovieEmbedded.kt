package od.konstantin.myapplication.data.local.models

import androidx.room.Embedded
import androidx.room.Relation


data class FavoriteMovieEmbedded(
    @Embedded
    val favoriteMovieEntity: FavoriteMovieEntity,
    @Relation(parentColumn = "favorite_movie_id", entityColumn = "id")
    val movieDetailsEntity: MovieDetailsEntity?,
    @Relation(parentColumn = "favorite_movie_id", entityColumn = "movie_id")
    val genres: List<MovieGenreEntity>
)