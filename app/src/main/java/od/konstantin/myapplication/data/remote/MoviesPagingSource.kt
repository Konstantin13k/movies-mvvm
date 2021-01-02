package od.konstantin.myapplication.data.remote

import android.util.Log
import androidx.paging.PagingSource
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MoviePoster
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val MOVIES_STARTING_PAGE_INDEX = 1
private const val MOVIE_RELEASE_DATE_FORMAT = "yyyy-MM-dd"

class MoviesPagingSource(private val moviesApi: MoviesApi) : PagingSource<Int, MoviePoster>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviePoster> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            val genresResponse = moviesApi.getGenres()
            val response = moviesApi.getMovies(position)
            val genres = genresResponse.genres.map { Genre(it) }
            val movies = response.movies.map {
                MoviePoster(
                    id = it.id,
                    title = it.title,
                    posterPicture = it.posterPicture ?: "",
                    genres = it.genreIds.map { genres.find { genre -> genre.id == it }!! },
                    ratings = it.ratings / 2,
                    votesCount = it.votesCount,
                    releaseDate = if (it.releaseDate.isNotEmpty()) {
                        SimpleDateFormat(
                            MOVIE_RELEASE_DATE_FORMAT,
                            Locale.getDefault()
                        ).parse(it.releaseDate)
                    } else null,
                    adult = it.adult,
                )
            }
            Log.d("PAGE", "page: $position | ${response.totalPages}")
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
}