package od.konstantin.myapplication.ui.favoritemovies

import dagger.Component
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FavoriteMoviesComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): FavoriteMoviesComponent
    }

    fun viewModelFactory(): FavoriteMoviesViewModelFactory

    fun viewModel(): FavoriteMoviesViewModel

    fun inject(fragment: FragmentFavoriteMovies)
}