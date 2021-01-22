package od.konstantin.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.local.MovieActorsDao
import od.konstantin.myapplication.data.local.MovieDetailsDao
import od.konstantin.myapplication.data.local.MovieGenresDao
import od.konstantin.myapplication.data.mappers.dto.MovieDetailDtoMapper
import od.konstantin.myapplication.data.mappers.entity.MovieDetailEntityMapper
import od.konstantin.myapplication.data.models.MovieDetail
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
    private val movieDetailsDao: MovieDetailsDao,
    private val movieActorsDao: MovieActorsDao,
    private val movieGenresDao: MovieGenresDao,
) {

    fun getMovieDetail(movieId: Int): LiveData<MovieDetail> {
        return Transformations.map(movieDetailsDao.getMovieDetails(movieId)) {
            it?.let { movieDetailsEntityMapper.map(it) }
        }
    }

    suspend fun updateMovieData(movieId: Int) = withContext(Dispatchers.IO) {
        val movieDetailDto = movieDetailsApi.getMovieDetail(movieId)
        val cast = movieActorsApi.getMovieCast(movieId)
        val actors = cast?.cast ?: emptyList()

        movieDetailDto?.let {
            val movieDetailsEntity = movieDetailDtoMapper.mapToEntity(it, actors)

            movieDetailsDao.insertMovie(movieDetailsEntity.movieDetailsEntity)
            movieActorsDao.insertActors(movieDetailsEntity.actors)
            movieGenresDao.insertGenres(movieDetailsEntity.genres)
        }
    }
}