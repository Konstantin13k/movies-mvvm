package od.konstantin.myapplication.data.models

import java.util.*

data class MoviePoster(
    val id: Int,
    val title: String,
    val posterPicture: String,
    var genres: List<Genre>,
    val ratings: Float,
    val votesCount: Int,
    val releaseDate: Date?,
    val adult: Boolean,
    var isFavorite: Boolean
)
