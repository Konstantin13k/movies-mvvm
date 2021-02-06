package od.konstantin.myapplication.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class ActorDetailsEmbedded(
    @Embedded
    val actorDetailsEntity: ActorDetailsEntity,
    @Relation(parentColumn = "actor_id", entityColumn = "actor_id")
    val movies: List<ActorMovieEntity>
)