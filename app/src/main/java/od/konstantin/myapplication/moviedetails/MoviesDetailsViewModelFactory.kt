package od.konstantin.myapplication.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import od.konstantin.myapplication.data.MoviesRepository

class MoviesDetailsViewModelFactory(private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(moviesRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}