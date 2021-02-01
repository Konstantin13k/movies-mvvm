package od.konstantin.myapplication.data.remote

import od.konstantin.myapplication.data.remote.models.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApi {


    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): MovieDetailDto?
}