package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import od.konstantin.myapplication.data.MoviesRepository

class MoviesListPageViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val moviesRepository: MoviesRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = when (modelClass) {
        MoviesListPageViewModel::class.java -> MoviesListPageViewModel(moviesRepository, handle)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}