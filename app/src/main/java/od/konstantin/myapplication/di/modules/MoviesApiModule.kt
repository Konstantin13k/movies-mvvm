package od.konstantin.myapplication.di.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import od.konstantin.myapplication.BuildConfig
import od.konstantin.myapplication.data.remote.ActorsApi
import od.konstantin.myapplication.data.remote.GenresApi
import od.konstantin.myapplication.data.remote.MovieDetailsApi
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.data.remote.interceptors.MoviesApiKeyInterceptor
import od.konstantin.myapplication.data.remote.interceptors.MoviesLanguageInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MoviesApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
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
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGenresApi(retrofit: Retrofit): GenresApi {
        return retrofit.create(GenresApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsApi(retrofit: Retrofit): MovieDetailsApi {
        return retrofit.create(MovieDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideActorsApi(retrofit: Retrofit): ActorsApi {
        return retrofit.create(ActorsApi::class.java)
    }
}