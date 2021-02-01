package od.konstantin.myapplication.ui.moviedetails

import dagger.Component
import od.konstantin.myapplication.di.components.AppComponent
import dagger.assisted.AssistedFactory
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface MovieDetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MovieDetailsComponent
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