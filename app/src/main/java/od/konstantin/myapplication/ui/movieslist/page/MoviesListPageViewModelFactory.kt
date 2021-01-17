package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import od.konstantin.myapplication.data.MoviesRepository
import javax.inject.Inject

class MoviesListPageViewModelFactory @Inject constructor(
    owner: SavedStateRegistryOwner,
    private val viewModelComponent: MoviesListPageComponent,
) : AbstractSavedStateViewModelFactory(owner, null) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = when (modelClass) {
        MoviesListPageViewModel::class.java -> viewModelComponent.viewModelComponent()
            .create(handle).provideViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}