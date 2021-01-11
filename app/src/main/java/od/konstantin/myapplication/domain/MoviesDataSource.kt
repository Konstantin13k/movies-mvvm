package od.konstantin.myapplication.domain

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.loadMovies
import od.konstantin.myapplication.data.models.Movie

class MoviesDataSource(private val context: Context) {

    suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        loadMovies(context)
    }

    suspend fun getMovie(movieId: Int): Movie? = withContext(Dispatchers.IO) {
        getMovies().find { it.id == movieId }
    }
}