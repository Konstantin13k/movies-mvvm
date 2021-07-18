package od.konstantin.myapplication.data

import android.util.Log
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.local.MovieActorsDao
import od.konstantin.myapplication.data.local.MovieDetailsDao
import od.konstantin.myapplication.data.local.MovieGenresDao
import od.konstantin.myapplication.data.local.MoviesDatabase
import od.konstantin.myapplication.data.mappers.dto.MovieDetailDtoMapper
import od.konstantin.myapplication.data.mappers.entity.MovieDetailEntityMapper
import od.konstantin.myapplication.data.models.MovieDetails
import od.konstantin.myapplication.data.remote.ActorsApi
import od.konstantin.myapplication.data.remote.MovieDetailsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(
    private val movieDetailsApi: MovieDetailsApi,
    private val movieActorsApi: ActorsApi,
    private val movieDetailDtoMapper: MovieDetailDtoMapper,
    private val movieDetailsEntityMapper: MovieDetailEntityMapper,
    private val moviesDatabase: MoviesDatabase,
    private val movieDetailsDao: MovieDetailsDao,
    private val movieActorsDao: MovieActorsDao,
    private val movieGenresDao: MovieGenresDao,
) {

    fun observeMovieDetailsUpdates(movieId: Int): Flow<MovieDetails?> {
        return movieDetailsDao.observeMovieDetailsUpdates(movieId).map {
            it?.let(movieDetailsEntityMapper::map)
        }
    }

    suspend fun updateMovieData(movieId: Int) = withContext(Dispatchers.IO) {
        try {
            val movieDetailDto = movieDetailsApi.getMovieDetail(movieId)
            val cast = movieActorsApi.getMovieCast(movieId)
            val actors = cast?.cast ?: emptyList()

            movieDetailDto?.let {
                val movieDetailsEntity = movieDetailDtoMapper.mapToEntity(it, actors)
                moviesDatabase.withTransaction {
                    movieDetailsDao.insertMovie(movieDetailsEntity.movieDetailsEntity)
                    movieActorsDao.insertActors(movieDetailsEntity.actors)
                    movieGenresDao.insertGenres(movieDetailsEntity.genres)
                }
            }
        } catch (e: Exception) {
            Log.e("NETWORK", null, e)
            // Todo Handle exceptions
        }
    }

    suspend fun updateMoviesDetails() = withContext(Dispatchers.IO) {
        val movieIds = movieDetailsDao.getMovieIds()
        for (movieId in movieIds) {
            updateMovieData(movieId)
        }
    }
}