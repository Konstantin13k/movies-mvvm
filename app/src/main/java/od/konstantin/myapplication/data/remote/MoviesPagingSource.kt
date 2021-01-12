package od.konstantin.myapplication.data.remote

import androidx.paging.PagingSource
import od.konstantin.myapplication.data.mappers.Mapper
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.models.GenreDto
import od.konstantin.myapplication.data.remote.models.MoviePosterDto
import od.konstantin.myapplication.ui.movieslist.MoviesSortType
import retrofit2.HttpException
import java.io.IOException

private const val MOVIES_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val sortType: MoviesSortType,
    private val moviePosterDtoMapper: Mapper<MoviePosterDto, MoviePoster>,
    private val genreDtoMapper: Mapper<GenreDto, Genre>,
) :
    PagingSource<Int, MoviePoster>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviePoster> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            val genresResponse = moviesApi.getGenres()
            val genres = genresResponse.genres.map { genreDtoMapper.map(it) }
            val response = loadMovies(position, sortType)
            val movies = response.movies.map {
                moviePosterDtoMapper.map(it).apply {
                    it.genreIds.mapNotNull { genres.find { genre -> genre.id == it } }
                }
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position == response.totalPages) null else position + 1,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private suspend fun loadMovies(page: Int, sortType: MoviesSortType) = when (sortType) {
        MoviesSortType.NowPlaying -> moviesApi.getNowPlayingMovies(page)
        MoviesSortType.Upcoming -> moviesApi.getUpcomingMovies(page)
        MoviesSortType.TopRated -> moviesApi.getTopRatedMovies(page)
        MoviesSortType.Popular -> moviesApi.getPopularMovies(page)
    }
}