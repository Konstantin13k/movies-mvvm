package od.konstantin.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import od.konstantin.myapplication.data.local.FavoriteMoviesDao
import od.konstantin.myapplication.data.local.models.FavoriteMovieEntity
import javax.inject.Inject

class FavoriteMoviesRepository @Inject constructor(
    private val favoriteMoviesDao: FavoriteMoviesDao
) {

    suspend fun setFavoriteMovie(movieId: Int, isFavorite: Boolean) {
        if (isFavorite) {
            addFavoriteMovie(movieId)
        } else {
            removeMovieFromFavorites(movieId)
        }
    }

    suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return favoriteMoviesDao.selectMovie(movieId) != null
    }

    fun isFavorite(movieId: Int): LiveData<Boolean> {
        return Transformations.map(favoriteMoviesDao.selectFavoriteMovie(movieId)) { it != null }
    }

    private suspend fun addFavoriteMovie(movieId: Int) {
        favoriteMoviesDao.insert(FavoriteMovieEntity(movieId))
    }

    private suspend fun removeMovieFromFavorites(movieId: Int) {
        favoriteMoviesDao.delete(movieId)
    }
}