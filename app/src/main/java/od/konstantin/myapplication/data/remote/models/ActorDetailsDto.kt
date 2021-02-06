package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDetailsDto(
    @SerialName("id")
    val personId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("birthday")
    val birthday: String?,
    @SerialName("place_of_birth")
    val placeOfBirth: String?,
    @SerialName("profile_path")
    val profilePicture: String?,
    @SerialName("biography")
    val biography: String,
)