package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.local.models.MovieDetailsEmbedded
import od.konstantin.myapplication.data.local.models.MovieDetailsEntity
import od.konstantin.myapplication.data.mappers.MoviesImageUrlMapper
import od.konstantin.myapplication.data.remote.models.ActorDto
import od.konstantin.myapplication.data.remote.models.MovieDetailDto
import od.konstantin.myapplication.utils.BackdropSizes
import od.konstantin.myapplication.utils.PosterSizes
import javax.inject.Inject

class MovieDetailDtoMapper @Inject constructor(
    private val genresMapper: GenreDtoMapper,
    private val actorDtoMapper: ActorDtoMapper,
    private val imageUrlMapper: MoviesImageUrlMapper
) {

    fun mapToEntity(
        movieDetailDto: MovieDetailDto,
        actorsDto: List<ActorDto>
    ): MovieDetailsEmbedded {
        with(movieDetailDto) {
            return MovieDetailsEmbedded(
                MovieDetailsEntity(
                    id = id,
                    title = title,
                    posterPicture = imageUrlMapper.mapUrl(PosterSizes.W300, posterPicture),
                    backdropPicture = imageUrlMapper.mapUrl(BackdropSizes.W780, backdropPicture),
                    ratings = ratings / 2,
                    votesCount = votesCount,
                    overview = overview ?: "",
                    runtime = runtime ?: 0,
                    adult = adult
                ),
                actorsDto.mapIndexed { movieRating, actorDto ->
                    actorDtoMapper.mapToEntity(
                        id,
                        movieRating,
                        actorDto
                    )
                },
                genres.map { genresMapper.mapToEntity(id, it) }
            )
        }
    }
}