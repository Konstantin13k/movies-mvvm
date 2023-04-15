package od.konstantin.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.local.FavoriteMoviesDao
import od.konstantin.myapplication.data.local.models.FavoriteMovieEntity
import od.konstantin.myapplication.data.mappers.entity.FavoriteMovieEntityMapper
import od.konstantin.myapplication.data.models.FavoriteMovie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMoviesRepository @Inject constructor(
    private val favoriteMoviesDao: FavoriteMoviesDao,
    private val favoriteMoviesMapper: FavoriteMovieEntityMapper,
    private val movieDetailsRepository: MovieDetailsRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeFavoriteMoviesUpdates(): Flow<List<FavoriteMovie>> =
        favoriteMoviesDao.observeFavoriteMovies().mapLatest { movies ->
            movies.mapNotNull { favoriteMoviesMapper.map(it) }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeFavoriteMovieUpdates(movieId: Int): Flow<Boolean> =
        favoriteMoviesDao.observeFavoriteMovieUpdates(movieId).mapLatest {
            it != null
        }

    suspend fun getFavoriteMovies(): List<FavoriteMovie> = withContext(Dispatchers.IO) {
        favoriteMoviesDao.getFavoriteMoviesEmbedded().mapNotNull { favoriteMoviesMapper.map(it) }
    }

    suspend fun updateFavoriteMovies() = withContext(Dispatchers.IO) {
        favoriteMoviesDao.getFavoriteMovies().forEach {
            movieDetailsRepository.updateMovieData(it.movieId)
        }
    }

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