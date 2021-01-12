package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.models.MoviePosterDto
import od.konstantin.myapplication.utils.PosterSizes

class MoviePosterDtoMapper : MoviesDtoMapper<MoviePosterDto, MoviePoster>() {

    override fun map(obj: MoviePosterDto): MoviePoster {
        with(obj) {
            return MoviePoster(
                id = id,
                title = title,
                posterPicture = mapImageUrl(PosterSizes.W300, posterPicture),
                genres = emptyList(),
                ratings = ratings / 2,
                votesCount = votesCount,
                releaseDate = mapReleaseDate(releaseDate),
                adult = adult,
            )
        }
    }
}