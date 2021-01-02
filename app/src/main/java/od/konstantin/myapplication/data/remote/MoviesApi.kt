package od.konstantin.myapplication.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import od.konstantin.myapplication.data.remote.models.JsonGenres
import od.konstantin.myapplication.data.remote.models.JsonMovies
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "9f722135bcc4ad7f85dd3f8292a9f2a3"
private const val API_KEY_HEADER = "api_key"
private const val DEFAULT_LANGUAGE = "en-US"

interface MoviesApi {

    @GET("movie/popular?$API_KEY_HEADER=$API_KEY")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("language") lang: String = DEFAULT_LANGUAGE
    ): JsonMovies

    @GET("genre/movie/list?$API_KEY_HEADER=$API_KEY")
    suspend fun getGenres(@Query("language") lang: String = DEFAULT_LANGUAGE): JsonGenres

    companion object {
        private class MoviesApiHeaderInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request =
                    chain.request().newBuilder().addHeader(API_KEY_HEADER, API_KEY).build()
                return chain.proceed(request)
            }
        }

        private val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(MoviesApiHeaderInterceptor())
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