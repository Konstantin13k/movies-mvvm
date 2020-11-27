package od.konstantin.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import od.konstantin.myapplication.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.movieslist.FragmentMoviesList

class MainActivity : AppCompatActivity(), FragmentMoviesList.ShowMovieDetailsListener,
    FragmentMoviesDetails.BackToMovieListListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviesListFragment = FragmentMoviesList()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.root_container, moviesListFragment)
                .commit()
        }
    }

    override fun showMovieDetails() {
        val movieDetailsFragment = FragmentMoviesDetails()
        supportFragmentManager.beginTransaction()
            .add(R.id.root_container, movieDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun backToMovieList() {
        supportFragmentManager.popBackStack()
    }
}