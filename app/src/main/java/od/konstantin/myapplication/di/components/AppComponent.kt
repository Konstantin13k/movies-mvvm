package od.konstantin.myapplication.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import od.konstantin.myapplication.MyApplication
import od.konstantin.myapplication.data.*
import od.konstantin.myapplication.di.modules.MoviesApiModule
import od.konstantin.myapplication.di.modules.MoviesDatabaseModule
import od.konstantin.myapplication.di.modules.MoviesWorkerModule
import javax.inject.Singleton

@Singleton
@Component(modules = [MoviesApiModule::class, MoviesDatabaseModule::class, MoviesWorkerModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(application: MyApplication)

    fun provideMoviesRepository(): MoviesRepository

    fun provideMovieDetailsRepository(): MovieDetailsRepository

    fun provideFavoriteMoviesRepository(): FavoriteMoviesRepository

    fun provideGenresRepository(): GenresRepository

    fun provideActorDetailsRepository(): ActorDetailsRepository
}