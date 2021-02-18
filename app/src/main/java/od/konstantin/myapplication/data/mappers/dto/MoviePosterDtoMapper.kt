package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.mappers.MoviesDateMapper
import od.konstantin.myapplication.data.mappers.MoviesImageUrlMapper
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.models.MoviePosterDto
import od.konstantin.myapplication.utils.PosterSizes
import javax.inject.Inject

class MoviePosterDtoMapper @Inject constructor(
    private val imageUrlMapper: MoviesImageUrlMapper,
    private val dateMapper: MoviesDateMapper
) {

    fun map(moviePosterDto: MoviePosterDto, genres: List<Genre>, isFavorite: Boolean): MoviePoster {
        with(moviePosterDto) {
            return MoviePoster(
                id = id,
                title = title,
                posterPicture = imageUrlMapper.mapUrl(PosterSizes.W300, posterPicture),
                genres = genres,
                ratings = ratings / 2,
                votesCount = votesCount,
                releaseDate = dateMapper.mapDate(releaseDate),
                adult = adult,
                isFavorite = isFavorite
            )
        }
    }
}