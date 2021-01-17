package od.konstantin.myapplication.ui.moviedetails

import dagger.Subcomponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Subcomponent
interface MovieDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieDetailsComponent
    }

    fun provideMovieDetailsViewModel(): MoviesDetailsViewModel

    fun inject(fragment: FragmentMoviesDetails)
}