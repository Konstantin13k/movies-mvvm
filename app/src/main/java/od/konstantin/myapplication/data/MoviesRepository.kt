package od.konstantin.myapplication.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MovieDetail
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.data.remote.MoviesPagingSource
import od.konstantin.myapplication.ui.movieslist.MoviesSortType

class MoviesRepository(private val moviesApi: MoviesApi) {

    fun getMovies(sortType: MoviesSortType): Flow<PagingData<MoviePoster>> {
        return Pager(PagingConfig(pageSize = 24)) {
            MoviesPagingSource(moviesApi, sortType)
        }.flow
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetail? = withContext(Dispatchers.IO) {
        val jsonMovie = async { moviesApi.getMovieDetail(movieId) }
        val cast = async { moviesApi.getMovieCast(movieId) }
        jsonMovie.await()?.let { jsonMovieDetail ->
            MovieDetail(
                id = jsonMovieDetail.id,
                title = jsonMovieDetail.title,
                backdropPicture = jsonMovieDetail.backdropPicture ?: "",
                genres = jsonMovieDetail.genres.map { Genre(it) },
                ratings = jsonMovieDetail.ratings,
                votesCount = jsonMovieDetail.votesCount,
                overview = jsonMovieDetail.overview ?: "",
                runtime = jsonMovieDetail.runtime ?: 0,
                adult = jsonMovieDetail.adult,
                actors = cast.await()?.cast?.map { Actor(it.id, it.name, it.profilePicture ?: "") }
                    ?: emptyList()
            )
        }
    }
}