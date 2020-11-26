package od.konstantin.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import od.konstantin.myapplication.moviedetails.FragmentMoviesDetails
import od.konstantin.myapplication.movieslist.FragmentMoviesList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviesListFragment = FragmentMoviesList.newInstance {
            supportFragmentManager.beginTransaction()
                .add(R.id.root_container, FragmentMoviesDetails())
                .addToBackStack(null)
                .commit()
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.root_container, moviesListFragment)
            .commit()
    }
}