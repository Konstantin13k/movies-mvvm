package od.konstantin.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
        val (fragment, rootContainer) = when (navigation) {
            Navigation.Back -> {
                supportFragmentManager.popBackStack()
                return
            }
            Navigation.ToMoviesList -> {
                FragmentMoviesList.newInstance() to R.id.main_fragment
            }
            Navigation.ToFavoriteMovies -> {
                FragmentFavoriteMovies.newInstance() to R.id.main_fragment
            }
            is Navigation.ToMovieDetails -> {
                FragmentMoviesDetails.newInstance(navigation.movieId) to R.id.root_container
            }
            is Navigation.ToActorDetails -> {
                FragmentActorDetails.newInstance(navigation.actorId) to R.id.root_container
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(rootContainer, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }.commit()
        Log.d("FRAGMS", "fragments: ${supportFragmentManager.fragments.size}")
    }
}