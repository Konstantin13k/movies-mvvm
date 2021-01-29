package od.konstantin.myapplication.ui.moviedetails

import dagger.Component
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface MovieDetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MovieDetailsComponent
    }

    fun provideMovieDetailsViewModel(): MoviesDetailsViewModel

    fun inject(fragment: FragmentMoviesDetails)
}