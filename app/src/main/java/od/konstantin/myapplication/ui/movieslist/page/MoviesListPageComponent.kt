package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.SavedStateHandle
import androidx.savedstate.SavedStateRegistryOwner
import dagger.BindsInstance
import dagger.Component
import dagger.assisted.AssistedFactory
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface MoviesListPageComponent {

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent,
            @BindsInstance owner: SavedStateRegistryOwner
        ): MoviesListPageComponent
    }

    fun viewModelFactory(): MoviesListPageViewModelProvider

    fun inject(fragment: FragmentMoviesListPage)

    @AssistedFactory
    interface MoviesListPageViewModelProvider {
        fun provideViewModel(savedStateHandle: SavedStateHandle): MoviesListPageViewModel
    }
}