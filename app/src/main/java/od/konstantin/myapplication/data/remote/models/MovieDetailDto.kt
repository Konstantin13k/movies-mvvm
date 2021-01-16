package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("backdrop_path")
    val backdropPicture: String?,
    @SerialName("genres")
    val genres: List<GenreDto>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    @SerialName("overview")
    val overview: String?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("adult")
    val adult: Boolean
)
