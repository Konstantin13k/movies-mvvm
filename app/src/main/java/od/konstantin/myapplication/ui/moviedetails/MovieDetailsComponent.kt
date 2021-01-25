package od.konstantin.myapplication.ui.moviedetails

import dagger.Subcomponent
import dagger.assisted.AssistedFactory
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Subcomponent
interface MovieDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieDetailsComponent
    }

    fun viewModelFactory(): MovieDetailsViewModelProvider

    fun viewModelFactoryProvider(): MovieDetailsViewModelFactoryProvider

    fun inject(fragment: FragmentMoviesDetails)

    @AssistedFactory
    interface MovieDetailsViewModelProvider {
        fun provideViewModel(movieId: Int): MoviesDetailsViewModel
    }

    @AssistedFactory
    interface MovieDetailsViewModelFactoryProvider {
        fun provideViewModelFactory(movieId: Int): MoviesDetailsViewModelFactory
    }
}