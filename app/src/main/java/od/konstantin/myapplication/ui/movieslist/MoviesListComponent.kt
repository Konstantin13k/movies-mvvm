package od.konstantin.myapplication.ui.movieslist

import dagger.Component
import dagger.Subcomponent
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface MoviesListComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MoviesListComponent
    }

    fun provideMoviesListViewModel(): MoviesListViewModel

    fun inject(fragment: FragmentMoviesList)
}