package od.konstantin.myapplication.data.mappers.entity

import od.konstantin.myapplication.data.local.models.MovieDetailsEmbedded
import od.konstantin.myapplication.data.models.MovieDetail
import javax.inject.Inject

class MovieDetailEntityMapper @Inject constructor(
    private val actorEntityMapper: ActorEntityMapper,
    private val genreEntityMapper: GenreEntityMapper,
) {

    fun map(movieDetailsEntity: MovieDetailsEmbedded): MovieDetail {
        with(movieDetailsEntity.movieDetailsEntity) {
            return MovieDetail(
                id = id,
                title = title,
                backdropPicture = backdropPicture,
                ratings = ratings,
                votesCount = votesCount,
                overview = overview,
                runtime = runtime,
                adult = adult,
                genres = movieDetailsEntity.genres.map { genreEntityMapper.map(it) },
                actors = movieDetailsEntity.actors.sortedBy { it.movieRating }
                    .map { actorEntityMapper.map(it) },
            )
        }
    }
}