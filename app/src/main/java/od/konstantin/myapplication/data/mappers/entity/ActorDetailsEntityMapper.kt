package od.konstantin.myapplication.data.mappers.entity

import od.konstantin.myapplication.data.local.models.ActorDetailsEmbedded
import od.konstantin.myapplication.data.mappers.MoviesDateMapper
import od.konstantin.myapplication.data.models.ActorDetails
import javax.inject.Inject

class ActorDetailsEntityMapper @Inject constructor(
    private val actorMovieEntityMapper: ActorMovieEntityMapper,
    private val moviesDateMapper: MoviesDateMapper,
) {

    fun map(actorDetailsEmbedded: ActorDetailsEmbedded): ActorDetails {
        with(actorDetailsEmbedded.actorDetailsEntity) {
            return ActorDetails(
                actorId = actorId,
                name = name,
                knownForDepartment = knownForDepartment,
                birthday = moviesDateMapper.mapDate(birthday),
                placeOfBirth = placeOfBirth,
                profilePicture = profilePicture,
                biography = biography,
                movies = actorDetailsEmbedded.movies.sortedBy { it.id }.map {
                    actorMovieEntityMapper.map(it)
                }
            )
        }
    }
}