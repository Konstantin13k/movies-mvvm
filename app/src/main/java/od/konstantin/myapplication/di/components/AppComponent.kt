package od.konstantin.myapplication.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.di.modules.MoviesApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = [MoviesApiModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun provideMoviesRepository(): MoviesRepository
}