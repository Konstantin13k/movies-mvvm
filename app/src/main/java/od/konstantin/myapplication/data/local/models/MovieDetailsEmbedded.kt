package od.konstantin.myapplication.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class MovieDetailsEmbedded(
    @Embedded
    val movieDetailsEntity: MovieDetailsEntity,
    @Relation(parentColumn = "id", entityColumn = "movie_id")
    val actors: List<MovieActorEntity>,
    @Relation(parentColumn = "id", entityColumn = "movie_id")
    val genres: List<MovieGenreEntity>
)