package od.konstantin.myapplication.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MoviesListViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}