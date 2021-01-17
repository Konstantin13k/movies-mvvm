package od.konstantin.myapplication.ui.movieslist.page

import androidx.savedstate.SavedStateRegistryOwner
import dagger.BindsInstance
import dagger.Subcomponent
import od.konstantin.myapplication.di.scopes.FragmentScope

@FragmentScope
@Subcomponent(modules = [MoviesListPageModule::class])
interface MoviesListPageComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance owner: SavedStateRegistryOwner): MoviesListPageComponent
    }

    fun viewModelComponent(): MoviesListPageViewModelComponent.Factory

    fun inject(fragment: FragmentMoviesListPage)
}