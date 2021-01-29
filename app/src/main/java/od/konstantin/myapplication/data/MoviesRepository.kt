package od.konstantin.myapplication.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.mappers.dto.GenreDtoMapper
import od.konstantin.myapplication.data.mappers.dto.MovieDetailDtoMapper
import od.konstantin.myapplication.data.mappers.dto.MoviePosterDtoMapper
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MovieDetail
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.data.remote.MoviesPagingSource
import od.konstantin.myapplication.ui.movieslist.MoviesSortType
import javax.inject.Inject
import javax.inject.Singleton

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviePosterDtoMapper: MoviePosterDtoMapper,
    private val movieDetailDtoMapper: MovieDetailDtoMapper,
    private val genreDtoMapper: GenreDtoMapper,
) {

    private var cachedGenres: Map<Int, Genre> = emptyMap()

    // Вынес маппинг из MoviesPagingSource, но потом прийдётся переписать и добавить обработку ошибок.
    // И не знаю на сколько хорошо вызывать suspend функцию из flow для каждого элемента в потоке.
    fun getMovies(sortType: MoviesSortType): Flow<PagingData<MoviePoster>> {
        return Pager(PagingConfig(pageSize = 24)) {
            MoviesPagingSource(moviesApi, sortType)
        }.flow.map { pagingData ->
            val genres = getGenres()
            pagingData.map { moviePosterDto ->
                moviePosterDtoMapper.map(moviePosterDto).apply {
                    this.genres = moviePosterDto.genreIds.mapNotNull { genres[it] }
                }
            }
        }
    }

    @Synchronized
    private suspend fun getGenres(): Map<Int, Genre> {
        if (cachedGenres.isEmpty()) {
            cachedGenres = moviesApi.getGenres().genres.map { genreDto ->
                genreDto.id to genreDtoMapper.map(genreDto)
            }.toMap()
        }
        return cachedGenres
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetail? = withContext(Dispatchers.IO) {
        val movieDetailDto = moviesApi.getMovieDetail(movieId)
        val cast = moviesApi.getMovieCast(movieId)
        val actors = cast?.cast ?: emptyList()

        movieDetailDto?.let { movieDetailDtoMapper.map(it, actors) }
    }
}