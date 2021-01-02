package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonGenres(
    @SerialName("genres")
    val genres: List<JsonGenre>
)

@Serializable
data class JsonGenre(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)