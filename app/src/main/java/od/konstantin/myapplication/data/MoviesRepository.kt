package od.konstantin.myapplication.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import od.konstantin.myapplication.data.mappers.dto.MoviePosterDtoMapper
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.data.remote.MoviesPagingSource
import od.konstantin.myapplication.ui.movieslist.MoviesSortType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviePosterDtoMapper: MoviePosterDtoMapper,
    private val genresRepository: GenresRepository,
) {

    fun getMovies(sortType: MoviesSortType): Flow<PagingData<MoviePoster>> {
        return Pager(PagingConfig(pageSize = 20)) {
            MoviesPagingSource(moviesApi, sortType)
        }.flow.map { pagingData ->
            pagingData.map { moviePosterDto ->
                moviePosterDtoMapper.map(moviePosterDto).also {
                    it.genres = genresRepository.getGenresByIds(moviePosterDto.genreIds)
                }
            }
        }
    }
}