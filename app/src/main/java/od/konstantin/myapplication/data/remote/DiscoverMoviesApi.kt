package od.konstantin.myapplication.data.remote

import od.konstantin.myapplication.data.remote.models.SmallMoviePostersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMoviesApi {

    @GET("discover/movie")
    suspend fun getMoviesWithGenres(
        @Query("with_genres") withGenres: String,
    ): SmallMoviePostersDto
}