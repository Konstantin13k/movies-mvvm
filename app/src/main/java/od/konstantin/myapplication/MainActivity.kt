package od.konstantin.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import od.konstantin.myapplication.ui.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.ui.movieslist.FragmentMoviesList
class MainActivity : AppCompatActivity(), FragmentMoviesList.ShowMovieDetailsListener,
    FragmentMoviesDetails.BackToMovieListListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val moviesListFragment = FragmentMoviesList()
            supportFragmentManager.beginTransaction()
                .replace(R.id.root_container, moviesListFragment)
                .commit()
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