package od.konstantin.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.movieslist.FragmentMoviesList

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

    override fun showMovieDetails(movie: Movie) {
        val movieDetailsFragment = FragmentMoviesDetails.newInstance(movie.id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, movieDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun backToMovieList() {
        supportFragmentManager.popBackStack()
    }
}