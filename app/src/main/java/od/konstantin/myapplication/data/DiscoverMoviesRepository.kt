package od.konstantin.myapplication.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import od.konstantin.myapplication.data.mappers.dto.SmallMoviePosterDtoMapper
import od.konstantin.myapplication.data.models.SmallMoviePoster
import od.konstantin.myapplication.data.remote.DiscoverMoviesApi
import javax.inject.Inject

class DiscoverMoviesRepository @Inject constructor(
    private val discoverMoviesApi: DiscoverMoviesApi,
    private val smallMoviePosterDtoMapper: SmallMoviePosterDtoMapper,
) {

    suspend fun getMoviesWithGenres(withGenreIds: List<Int>): List<SmallMoviePoster> =
        withContext(Dispatchers.IO) {
            discoverMoviesApi.getMoviesWithGenres(withGenreIds.joinToString(", "))
                .movies.map {
                    smallMoviePosterDtoMapper.map(it)
                }
        }
}