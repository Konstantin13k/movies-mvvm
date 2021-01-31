package od.konstantin.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.local.FavoriteMoviesDao
import od.konstantin.myapplication.data.local.models.FavoriteMovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMoviesRepository @Inject constructor(
    private val favoriteMoviesDao: FavoriteMoviesDao
) {

    suspend fun setFavoriteMovie(movieId: Int, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        if (isFavorite) {
            addFavoriteMovie(movieId)
        } else {
            removeMovieFromFavorites(movieId)
        }
    }

    suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return favoriteMoviesDao.selectMovie(movieId) != null
    }

    private suspend fun addFavoriteMovie(movieId: Int) {
        favoriteMoviesDao.insert(FavoriteMovieEntity(movieId))
    }

    private suspend fun removeMovieFromFavorites(movieId: Int) {
        favoriteMoviesDao.delete(movieId)
    }
}