package od.konstantin.myapplication.ui

interface FragmentNavigator {
    sealed class Navigation {
        object Back : Navigation()
        object ToMoviesList : Navigation()
        object ToFavoriteMovies : Navigation()
        data class ToMovieDetails(val movieId: Int) : Navigation()
        data class ToActorDetails(val actorId: Int) : Navigation()
    }

    fun navigate(navigation: Navigation, addToBackStack: Boolean = false)
}