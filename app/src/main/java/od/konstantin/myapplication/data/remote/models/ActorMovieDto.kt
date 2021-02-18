package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorMovieDto(
    @SerialName("id")
    val movieId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String?,
)