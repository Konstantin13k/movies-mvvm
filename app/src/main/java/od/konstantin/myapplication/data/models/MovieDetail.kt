package od.konstantin.myapplication.data.models

data class MovieDetail(
    val id: Int,
    val title: String,
    val backdropPicture: String,
    val genres: List<Genre>,
    val ratings: Float,
    val votesCount: Int,
    val overview: String,
    val runtime: Int,
    val adult: Boolean,
    val actors: List<Actor>,
)
