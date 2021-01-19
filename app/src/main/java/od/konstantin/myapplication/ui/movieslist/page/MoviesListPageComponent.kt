package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.SavedStateHandle
import androidx.savedstate.SavedStateRegistryOwner
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.assisted.AssistedFactory
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Subcomponent
interface MoviesListPageComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance owner: SavedStateRegistryOwner): MoviesListPageComponent
    }

    fun viewModelFactory(): MoviesListPageViewModelProvider

    fun inject(fragment: FragmentMoviesListPage)

    @AssistedFactory
    interface MoviesListPageViewModelProvider {
        fun provideViewModel(savedStateHandle: SavedStateHandle): MoviesListPageViewModel
    }
}