package od.konstantin.myapplication.data.models

data class FavoriteMovie(
    val movieId: Int,
    val title: String,
    val backdropPicture: String,
    val posterPicture: String,
    val genres: List<Genre>,
    val ratings: Float,
    val votesCount: Int,
    val overview: String,
    val runtime: Int,
    val adult: Boolean,
)