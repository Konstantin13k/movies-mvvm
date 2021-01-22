package od.konstantin.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.mappers.dto.MovieDetailDtoMapper
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
) {

    suspend fun getMovieDetail(movieId: Int): MovieDetail? = withContext(Dispatchers.IO) {
        val movieDetailDto = movieDetailsApi.getMovieDetail(movieId)
        val cast = movieActorsApi.getMovieCast(movieId)
        val actors = cast?.cast ?: emptyList()

        movieDetailDto?.let { movieDetailDtoMapper.map(it, actors) }
    }
}