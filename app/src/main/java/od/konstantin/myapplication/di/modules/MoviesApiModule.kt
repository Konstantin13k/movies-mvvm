package od.konstantin.myapplication.di.modules

import dagger.Module
import dagger.Provides
import od.konstantin.myapplication.data.remote.MoviesApi

@Module
class MoviesApiModule {

    @Provides
    fun provideMoviesApi(): MoviesApi {
        return MoviesApi.moviesApi
    }
}