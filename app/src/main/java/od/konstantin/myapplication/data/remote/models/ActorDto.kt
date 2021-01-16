package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String?
)