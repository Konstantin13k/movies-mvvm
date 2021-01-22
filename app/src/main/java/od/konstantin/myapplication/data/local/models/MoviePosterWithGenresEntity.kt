package od.konstantin.myapplication.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class MoviePosterWithGenresEntity(
    @Embedded
    var moviePoster: MoviePosterEntity,
    @Relation(parentColumn = "id", entityColumn = "movie_id")
    var movieGenres: List<MovieGenreEntity>,
)