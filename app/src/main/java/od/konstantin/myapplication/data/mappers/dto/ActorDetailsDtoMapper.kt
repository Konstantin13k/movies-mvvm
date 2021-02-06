package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.local.models.ActorDetailsEntity
import od.konstantin.myapplication.data.mappers.MoviesImageUrlMapper
import od.konstantin.myapplication.data.remote.models.ActorDetailsDto
import od.konstantin.myapplication.utils.ActorPictureSizes
import javax.inject.Inject

class ActorDetailsDtoMapper @Inject constructor(private val imageUrlMapper: MoviesImageUrlMapper) {

    fun mapToEntity(actorDetails: ActorDetailsDto): ActorDetailsEntity {
        with(actorDetails) {
            return ActorDetailsEntity(
                actorId = personId,
                name = name,
                knownForDepartment = knownForDepartment,
                birthday = birthday ?: "",
                placeOfBirth = placeOfBirth ?: "",
                profilePicture = imageUrlMapper.mapUrl(ActorPictureSizes.H632, profilePicture),
                biography = biography,
            )
        }
    }
}