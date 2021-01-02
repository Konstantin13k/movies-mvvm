package od.konstantin.myapplication.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.domain.MoviesDataSource

class MoviesListViewModelFactory(
    private val moviesDataSource: MoviesDataSource,
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(moviesDataSource, moviesRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}