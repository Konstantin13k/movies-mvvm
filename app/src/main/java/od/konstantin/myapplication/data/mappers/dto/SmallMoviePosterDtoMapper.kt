package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.models.SmallMoviePoster
import od.konstantin.myapplication.data.remote.models.SmallMoviePosterDto
import javax.inject.Inject

class SmallMoviePosterDtoMapper @Inject constructor() {

    fun map(smallMoviePosterDto: SmallMoviePosterDto): SmallMoviePoster {
        with(smallMoviePosterDto) {
            return SmallMoviePoster(
                movieId = movieId,
                title = title,
                overview = overview,
                ratings = ratings / 2,
                votesCount = votesCount,
            )
        }
    }
}