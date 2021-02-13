package od.konstantin.myapplication.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmallMoviePosterDto(
    @SerialName("id")
    val movieId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
)