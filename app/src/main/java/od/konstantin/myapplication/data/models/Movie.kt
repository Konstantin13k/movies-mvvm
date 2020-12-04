package od.konstantin.myapplication.data.models

data class Movie(
    val posterId: Int,
    val smallPosterId: Int,
    val movieTitle: String,
    val tags: List<String>,
    val pg: Int,
    val rating: Float,
    val reviews: Int,
    val storyline: String,
    val movieLength: Int,
    val actors: List<Actor> = emptyList(),
)