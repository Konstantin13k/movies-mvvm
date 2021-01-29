package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.mappers.MoviesImageUrlMapper
import od.konstantin.myapplication.data.models.MovieDetail
import od.konstantin.myapplication.data.remote.models.ActorDto
import od.konstantin.myapplication.data.remote.models.MovieDetailDto
import od.konstantin.myapplication.utils.BackdropSizes
import javax.inject.Inject

class MovieDetailDtoMapper @Inject constructor(
    private val genresMapper: GenreDtoMapper,
    private val actorDtoMapper: ActorDtoMapper,
    private val imageUrlMapper: MoviesImageUrlMapper
) {

    fun map(movieDetailDto: MovieDetailDto, actorsDto: List<ActorDto>): MovieDetail {
        with(movieDetailDto) {
            return MovieDetail(
                id = id,
                title = title,
                backdropPicture = imageUrlMapper.mapUrl(BackdropSizes.W780, backdropPicture),
                genres = genres.map { genresMapper.map(it) },
                ratings = ratings / 2,
                votesCount = votesCount,
                overview = overview ?: "",
                runtime = runtime ?: 0,
                adult = adult,
                actors = actorsDto.map { actorDtoMapper.map(it) }
            )
        }
    }
}