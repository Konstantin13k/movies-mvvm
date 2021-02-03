package od.konstantin.myapplication.di.modules

import androidx.work.Configuration
import dagger.Module
import dagger.Provides
import od.konstantin.myapplication.MoviesWorkerFactory
import javax.inject.Singleton

@Module
class MoviesWorkerModule {

    @Singleton
    @Provides
    fun provideWorkManagerConfiguration(
        moviesWorkerFactory: MoviesWorkerFactory
    ): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(moviesWorkerFactory)
            .build()
    }
}