package od.konstantin.myapplication.ui.movieslist

import dagger.Subcomponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Subcomponent
interface MoviesListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MoviesListComponent
    }

    fun provideMoviesListViewModel(): MoviesListViewModel

    fun inject(fragment: FragmentMoviesList)
}