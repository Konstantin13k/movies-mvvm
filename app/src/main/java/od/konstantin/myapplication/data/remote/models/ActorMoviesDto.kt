package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorMoviesDto(
    @SerialName("results")
    val movies: List<ActorMovieDto>,
)