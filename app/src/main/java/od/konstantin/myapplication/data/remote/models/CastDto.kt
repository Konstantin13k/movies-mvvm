package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    @SerialName("id")
    val movieId: Int,
    @SerialName("cast")
    val cast: List<ActorDto>
)