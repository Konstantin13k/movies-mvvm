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
) :
    PagingSource<Int, MoviePosterDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviePosterDto> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            val response = loadMovies(position, sortType)
            LoadResult.Page(
                data = response.movies,
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