package od.konstantin.myapplication.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import od.konstantin.myapplication.domain.MoviesDataSource

class MoviesDetailsViewModelFactory(private val moviesDataSource: MoviesDataSource) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(moviesDataSource)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}