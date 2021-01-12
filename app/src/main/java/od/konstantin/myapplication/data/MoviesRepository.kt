package od.konstantin.myapplication.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.mappers.Mapper
import od.konstantin.myapplication.data.mappers.dto.ActorDtoMapper
import od.konstantin.myapplication.data.mappers.dto.GenreDtoMapper
import od.konstantin.myapplication.data.mappers.dto.MovieDetailDtoMapper
import od.konstantin.myapplication.data.mappers.dto.MoviePosterDtoMapper
import od.konstantin.myapplication.data.models.Actor
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.MovieDetail
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.data.remote.MoviesPagingSource
import od.konstantin.myapplication.data.remote.models.ActorDto
import od.konstantin.myapplication.data.remote.models.GenreDto
import od.konstantin.myapplication.data.remote.models.MovieDetailDto
import od.konstantin.myapplication.data.remote.models.MoviePosterDto
import od.konstantin.myapplication.ui.movieslist.MoviesSortType

class MoviesRepository(
    private val moviesApi: MoviesApi,
    private val moviePosterDtoMapper: Mapper<MoviePosterDto, MoviePoster>,
    private val movieDetailDtoMapper: Mapper<MovieDetailDto, MovieDetail>,
    private val actorDtoMapper: Mapper<ActorDto, Actor>,
    private val genreDtoMapper: Mapper<GenreDto, Genre>,
) {

    fun getMovies(sortType: MoviesSortType): Flow<PagingData<MoviePoster>> {
        return Pager(PagingConfig(pageSize = 24)) {
            MoviesPagingSource(moviesApi, sortType, moviePosterDtoMapper, genreDtoMapper)
        }.flow
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetail? = withContext(Dispatchers.IO) {
        val jsonMovie = async { moviesApi.getMovieDetail(movieId) }
        val cast = async { moviesApi.getMovieCast(movieId) }

        jsonMovie.await()?.let { movieDetailDto ->
            val movieDetail = movieDetailDtoMapper.map(movieDetailDto)
            val actors = cast.await()?.cast?.map { actorDtoMapper.map(it) } ?: emptyList()
            movieDetail.apply {
                this.actors = actors
            }
        }
    }

    companion object {
        fun getRepository(): MoviesRepository {
            return MoviesRepository(
                MoviesApi.moviesApi,
                MoviePosterDtoMapper(),
                MovieDetailDtoMapper(GenreDtoMapper()),
                ActorDtoMapper(),
                GenreDtoMapper()
            )
        }
    }
}