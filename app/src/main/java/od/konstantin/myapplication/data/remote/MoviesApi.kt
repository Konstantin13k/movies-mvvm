package od.konstantin.myapplication.data.remote

import od.konstantin.myapplication.data.remote.models.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): MoviesDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MoviesDto
}