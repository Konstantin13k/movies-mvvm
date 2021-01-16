package od.konstantin.myapplication.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import od.konstantin.myapplication.BuildConfig
import od.konstantin.myapplication.data.remote.interceptors.MoviesApiKeyInterceptor
import od.konstantin.myapplication.data.remote.interceptors.MoviesLanguageInterceptor
import od.konstantin.myapplication.data.remote.models.CastDto
import od.konstantin.myapplication.data.remote.models.GenresDto
import od.konstantin.myapplication.data.remote.models.MovieDetailDto
import od.konstantin.myapplication.data.remote.models.MoviesDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

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

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): MovieDetailDto?

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
    ): CastDto?

    companion object {

        private val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            })
            .addInterceptor(MoviesApiKeyInterceptor())
            .addInterceptor(MoviesLanguageInterceptor())
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()

        val moviesApi: MoviesApi = retrofit.create()
    }
}