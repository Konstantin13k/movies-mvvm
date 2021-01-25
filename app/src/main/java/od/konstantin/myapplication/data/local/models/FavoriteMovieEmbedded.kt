package od.konstantin.myapplication.data.local.models

import androidx.room.Embedded

data class FavoriteMovieEmbedded(
    @Embedded
    val favoriteMovieEntity: FavoriteMovieEntity,
//    @Embedded
//    val movieDetailsEmbedded: MovieDetailsEmbedded
    /*@Relation(parentColumn = "movie_id", entityColumn = "id")
    val movieDetailsEmbedded: MovieDetailsEntity,
    @Relation(parentColumn = "movie_id", entityColumn = "movie_id")
    val actors: List<MovieActorEntity>,
    @Relation(parentColumn = "movie_id", entityColumn = "movie_id")
    val genres: List<MovieGenreEntity>*/
)