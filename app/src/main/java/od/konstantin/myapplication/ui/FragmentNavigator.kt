package od.konstantin.myapplication.ui

import android.view.View

interface FragmentNavigator {
    sealed class Navigation {
        object Back : Navigation()
        object ToMoviesList : Navigation()
        object ToFavoriteMovies : Navigation()
        data class ToMovieDetails(val movieId: Int, val movieCardView: View? = null) : Navigation()
        data class ToActorDetails(val actorId: Int, val actorCardView: View? = null) : Navigation()
    }

    fun navigate(navigation: Navigation, addToBackStack: Boolean = true)
}