package od.konstantin.myapplication.data.remote

import androidx.paging.PagingSource
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.models.JsonMovie
import od.konstantin.myapplication.movieslist.MoviesSortType
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val MOVIES_STARTING_PAGE_INDEX = 1
private const val MOVIE_RELEASE_DATE_FORMAT = "yyyy-MM-dd"

class MoviesPagingSource(private val moviesApi: MoviesApi, private val sortType: MoviesSortType) :
    PagingSource<Int, MoviePoster>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviePoster> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            val genresResponse = moviesApi.getGenres()
            val response = loadMovies(position, sortType)
            val genres = genresResponse.genres.map { Genre(it) }
            val movies = response.movies.map { it.toMoviePoster(genres) }
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

    private fun JsonMovie.toMoviePoster(genres: List<Genre>): MoviePoster {
        return MoviePoster(
            id = id,
            title = title,
            posterPicture = posterPicture ?: "",
            genres = genreIds.toGenres(genres),
            ratings = ratings / 2,
            votesCount = votesCount,
            releaseDate = if (releaseDate.isNotEmpty()) {
                SimpleDateFormat(
                    MOVIE_RELEASE_DATE_FORMAT,
                    Locale.getDefault()
                ).parse(releaseDate)
            } else null,
            adult = adult,
        )
    }

    private fun List<Int>.toGenres(genres: List<Genre>): List<Genre> {
        val newGenres: MutableList<Genre> = arrayListOf()
        this.forEach { genres.find { genre -> genre.id == it }?.let { newGenres.add(it) } }
        return newGenres
    }
}