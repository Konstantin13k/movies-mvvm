package od.konstantin.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import od.konstantin.myapplication.MyApplication
import od.konstantin.myapplication.R
import od.konstantin.myapplication.ui.FragmentNavigator.Navigation
import od.konstantin.myapplication.ui.actordetails.FragmentActorDetails
import od.konstantin.myapplication.ui.favoritemovies.FragmentFavoriteMovies
import od.konstantin.myapplication.ui.main.FragmentMain
import od.konstantin.myapplication.ui.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.ui.movieslist.FragmentMoviesList

class MainActivity : AppCompatActivity(), FragmentNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            val moviesListFragment = FragmentMain.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, moviesListFragment)
                .commit()

            intent?.let(::handleIntent)
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                intent.data?.lastPathSegment?.toIntOrNull()?.let { movieId ->
                    (application as MyApplication).movieNotifications.dismissNotification(movieId)
                    navigate(Navigation.ToMovieDetails(movieId))
                }
            }
        }
    }

    override fun navigate(navigation: Navigation, addToBackStack: Boolean) {
        val navigationInfo: NavigationInfo = when (navigation) {
            Navigation.Back -> {
                supportFragmentManager.popBackStack()
                return
            }
            Navigation.ToMoviesList -> {
                NavigationInfo(
                    fragment = FragmentMoviesList.newInstance(),
                    rootContainerId = R.id.main_fragment
                )
            }
            Navigation.ToFavoriteMovies -> {
                NavigationInfo(
                    fragment = FragmentFavoriteMovies.newInstance(),
                    rootContainerId = R.id.main_fragment
                )
            }
            is Navigation.ToMovieDetails -> {
                val movieTransitionName = getString(R.string.movie_poster_details_transition_name)
                NavigationInfo(
                    fragment = FragmentMoviesDetails.newInstance(navigation.movieId),
                    rootContainerId = R.id.main_fragment,
                    addToBackStack = addToBackStack,
                    sharedView = navigation.movieCardView,
                    sharedViewTransitionName = movieTransitionName
                )
            }
            is Navigation.ToActorDetails -> {
                val actorTransitionName = getString(R.string.actor_poster_details_transition_name)
                NavigationInfo(
                    fragment = FragmentActorDetails.newInstance(navigation.actorId),
                    rootContainerId = R.id.main_fragment,
                    addToBackStack = addToBackStack,
                    sharedView = navigation.actorCardView,
                    sharedViewTransitionName = actorTransitionName
                )
            }
        }
        navigate(navigationInfo)
    }

    private fun navigate(navigationInfo: NavigationInfo) {
        val fragment = navigationInfo.fragment
        val rootContainerId = navigationInfo.rootContainerId
        val addToBackStack = navigationInfo.addToBackStack
        val sharedViewTransitionName = navigationInfo.sharedViewTransitionName

        supportFragmentManager.beginTransaction().apply {
            replace(rootContainerId, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
            navigationInfo.sharedView?.let { sharedView ->
                addSharedElement(sharedView, sharedViewTransitionName)
            }
        }.commit()
    }

    private class NavigationInfo(
        val fragment: Fragment,
        val rootContainerId: Int,
        val addToBackStack: Boolean = false,
        val sharedView: View? = null,
        val sharedViewTransitionName: String = ""
    )
}