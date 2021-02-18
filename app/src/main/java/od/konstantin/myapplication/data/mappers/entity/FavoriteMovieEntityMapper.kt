package od.konstantin.myapplication.data.mappers.entity

import od.konstantin.myapplication.data.local.models.FavoriteMovieEmbedded
import od.konstantin.myapplication.data.models.FavoriteMovie
import javax.inject.Inject

class FavoriteMovieEntityMapper @Inject constructor(private val genreEntityMapper: GenreEntityMapper) {

    fun map(favoriteMovieEmbedded: FavoriteMovieEmbedded): FavoriteMovie? {
        favoriteMovieEmbedded.movieDetailsEntity?.let { detailsEntity ->
            with(detailsEntity) {
                return FavoriteMovie(
                    movieId = id,
                    title = title,
                    backdropPicture = backdropPicture,
                    posterPicture = posterPicture,
                    ratings = ratings,
                    votesCount = votesCount,
                    overview = overview,
                    runtime = runtime,
                    adult = adult,
                    genres = favoriteMovieEmbedded.genres.map { genreEntityMapper.map(it) },
                )
            }
        }
        return null
    }
}