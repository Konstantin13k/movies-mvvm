package od.konstantin.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import od.konstantin.myapplication.MyApplication
import od.konstantin.myapplication.R
import od.konstantin.myapplication.ui.main.FragmentMain
import od.konstantin.myapplication.ui.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.ui.movieslist.FragmentMoviesList

class MainActivity : AppCompatActivity(), FragmentMoviesList.ShowMovieDetailsListener,
    FragmentMoviesDetails.BackToMovieListListener {

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
                    val movieDetailsFragment = FragmentMoviesDetails.newInstance(movieId)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.root_container, movieDetailsFragment)
                        .commit()
                }
            }
        }
    }

    override fun showMovieDetails(movieId: Int) {
        val movieDetailsFragment = FragmentMoviesDetails.newInstance(movieId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, movieDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun backToMovieList() {
        supportFragmentManager.popBackStack()
    }
}