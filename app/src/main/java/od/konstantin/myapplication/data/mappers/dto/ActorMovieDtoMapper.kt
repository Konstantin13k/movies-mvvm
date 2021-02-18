package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.local.models.ActorMovieEntity
import od.konstantin.myapplication.data.mappers.MoviesImageUrlMapper
import od.konstantin.myapplication.data.remote.models.ActorMovieDto
import od.konstantin.myapplication.utils.PosterSizes
import javax.inject.Inject

class ActorMovieDtoMapper @Inject constructor(private val imageUrlMapper: MoviesImageUrlMapper) {

    fun mapToEntity(actorId: Int, actorMovie: ActorMovieDto): ActorMovieEntity {
        with(actorMovie) {
            return ActorMovieEntity(
                movieId = movieId,
                title = title,
                posterPicture = imageUrlMapper.mapUrl(PosterSizes.W300, posterPicture),
                actorId = actorId
            )
        }
    }
}