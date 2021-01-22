package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.local.models.MovieActorEntity
import od.konstantin.myapplication.data.mappers.MoviesImageUrlMapper
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.data.remote.models.ActorDto
import od.konstantin.myapplication.utils.ActorPictureSizes
import javax.inject.Inject

class ActorDtoMapper @Inject constructor(private val imageUrlMapper: MoviesImageUrlMapper) {

    fun map(actorDto: ActorDto): Actor {
        return Actor(
            actorDto.id,
            actorDto.name,
            imageUrlMapper.mapUrl(ActorPictureSizes.W185, actorDto.profilePicture)
        )
    }

    fun mapToEntity(movieId: Int, movieRating: Int, actorDto: ActorDto): MovieActorEntity {
        return MovieActorEntity(
            actorId = actorDto.id,
            name = actorDto.name,
            picture = imageUrlMapper.mapUrl(ActorPictureSizes.W185, actorDto.profilePicture),
            movieId = movieId,
            movieRating = movieRating
        )
    }
}