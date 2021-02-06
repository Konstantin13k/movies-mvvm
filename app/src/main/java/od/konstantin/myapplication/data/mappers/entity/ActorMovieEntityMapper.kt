package od.konstantin.myapplication.data.mappers.entity

import od.konstantin.myapplication.data.local.models.ActorMovieEntity
import od.konstantin.myapplication.data.models.ActorMovie
import javax.inject.Inject

class ActorMovieEntityMapper @Inject constructor() {

    fun map(actorMovieEntity: ActorMovieEntity): ActorMovie {
        return ActorMovie(
            actorMovieEntity.movieId,
            actorMovieEntity.title,
            actorMovieEntity.posterPicture
        )
    }
}