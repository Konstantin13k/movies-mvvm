package od.konstantin.myapplication.data.models

data class SmallMoviePoster(
    val movieId: Int,
    val title: String,
    val overview: String,
    val ratings: Float,
    val votesCount: Int,
)