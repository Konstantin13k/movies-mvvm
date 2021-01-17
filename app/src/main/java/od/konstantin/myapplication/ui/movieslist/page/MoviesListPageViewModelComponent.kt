package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.SavedStateHandle
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface MoviesListPageViewModelComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance savedStateHandle: SavedStateHandle): MoviesListPageViewModelComponent
    }

    fun provideViewModel(): MoviesListPageViewModel
}