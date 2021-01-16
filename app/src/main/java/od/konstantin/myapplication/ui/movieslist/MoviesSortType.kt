package od.konstantin.myapplication.ui.movieslist

enum class MoviesSortType(val id: Int) {

    NOW_PLAYING(0), UPCOMING(1), TOP_RATED(2), POPULAR(3);

    companion object {
        fun getSortType(id: Int): MoviesSortType {
            return values()[id]
        }
    }
}
