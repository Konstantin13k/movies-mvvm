package od.konstantin.myapplication.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import od.konstantin.myapplication.BuildConfig
import od.konstantin.myapplication.data.remote.models.JsonCast
import od.konstantin.myapplication.data.remote.models.JsonGenres
import od.konstantin.myapplication.data.remote.models.JsonMovieDetail
import od.konstantin.myapplication.data.remote.models.JsonMovies
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val QUERY_KEY_API_KEY = "api_key"
private const val QUERY_KEY_LANGUAGE = "language"
private const val DEFAULT_LANGUAGE = "en-US"

interface MoviesApi {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): JsonMovies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): JsonMovies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): JsonMovies

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): JsonMovies

    @GET("genre/movie/list")
    suspend fun getGenres(): JsonGenres

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): JsonMovieDetail?

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
    ): JsonCast?

    companion object {
        private class MoviesApiKeyInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val url = request.url.newBuilder()
                        .addQueryParameter(QUERY_KEY_API_KEY, BuildConfig.MOVIES_API_KEY).build()
                request = request.newBuilder().url(url).build()
                return chain.proceed(request)
            }
        }

        private class MoviesLanguageInterceptor(private val language: String = DEFAULT_LANGUAGE) :
            Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val url = request.url.newBuilder()
                        .addQueryParameter(QUERY_KEY_LANGUAGE, language).build()
                request = request.newBuilder().url(url).build()
                return chain.proceed(request)
            }
        }

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