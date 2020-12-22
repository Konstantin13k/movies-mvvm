package od.konstantin.myapplication.domain

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.loadMovies
import od.konstantin.myapplication.data.models.Movie

class MoviesDataSource {

    suspend fun getMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
        loadMovies(context)
    }

    suspend fun getMovie(context: Context, movieId: Int): Movie? = withContext(Dispatchers.IO) {
        getMovies(context).find { it.id == movieId }
    }
}