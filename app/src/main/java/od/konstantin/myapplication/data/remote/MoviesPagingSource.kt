package od.konstantin.myapplication.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
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
            val response = try {
                loadMovies(position, sortType)
            } catch (e: Exception) {
                Log.e("NETWORK", null, e)
                // Todo Handle exceptions
                null
            }
            LoadResult.Page(
                data = response?.movies ?: emptyList(),
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position == response?.totalPages ?: position) null else position + 1,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private suspend fun loadMovies(page: Int, sortType: MoviesSortType) = when (sortType) {
        MoviesSortType.NOW_PLAYING -> moviesApi.getNowPlayingMovies(page)
        MoviesSortType.UPCOMING -> moviesApi.getUpcomingMovies(page)
        MoviesSortType.TOP_RATED -> moviesApi.getTopRatedMovies(page)
        MoviesSortType.POPULAR -> moviesApi.getPopularMovies(page)
    }

    override fun getRefreshKey(state: PagingState<Int, MoviePosterDto>): Int? {
        return state.anchorPosition
    }
}