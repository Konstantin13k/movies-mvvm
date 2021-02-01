package od.konstantin.myapplication.data.mappers.entity

import od.konstantin.myapplication.data.local.models.MovieActorEntity
import od.konstantin.myapplication.data.models.Actor
import javax.inject.Inject

class ActorEntityMapper @Inject constructor() {

    fun map(actorEntity: MovieActorEntity): Actor {
        return Actor(
            actorEntity.actorId,
            actorEntity.name,
            actorEntity.picture
        )
    }
}