package od.konstantin.myapplication.movieslist

sealed class MoviesSortType(val id: Int) {
    object NowPlaying : MoviesSortType(0)
    object Upcoming : MoviesSortType(1)
    object TopRated : MoviesSortType(2)
    object Popular : MoviesSortType(3)

    companion object {
        fun getSortType(id: Int): MoviesSortType = when (id) {
            0 -> NowPlaying
            1 -> Upcoming
            2 -> TopRated
            3 -> Popular
            else -> throw IllegalArgumentException("Not found sort type with id: $id")
        }
    }
}
