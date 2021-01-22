package od.konstantin.myapplication.data.mappers.entity

import od.konstantin.myapplication.data.local.models.MoviePosterEntity
import od.konstantin.myapplication.data.mappers.MoviesReleaseDateMapper
import od.konstantin.myapplication.data.models.MoviePoster
import javax.inject.Inject

class MoviePosterEntityMapper @Inject constructor(
    private val releaseDateMapper: MoviesReleaseDateMapper
) {

    fun map(moviePosterEntity: MoviePosterEntity): MoviePoster {
        with(moviePosterEntity) {
            return MoviePoster(
                id = id,
                title = title,
                posterPicture = posterPicture,
                genres = emptyList(),
                ratings = ratings,
                votesCount = votesCount,
                releaseDate = releaseDateMapper.mapDate(releaseDate),
                adult = adult,
            )
        }
    }
}