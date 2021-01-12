package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.data.remote.models.ActorDto
import od.konstantin.myapplication.utils.ActorPictureSizes

class ActorDtoMapper : MoviesDtoMapper<ActorDto, Actor>() {

    override fun map(obj: ActorDto): Actor {
        return Actor(
            obj.id,
            obj.name,
            mapImageUrl(ActorPictureSizes.W185, obj.profilePicture)
        )
    }
}