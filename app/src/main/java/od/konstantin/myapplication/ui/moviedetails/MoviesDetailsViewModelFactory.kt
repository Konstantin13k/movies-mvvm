package od.konstantin.myapplication.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MoviesDetailsViewModelFactory @AssistedInject constructor(
    private val movieDetailsComponent: MovieDetailsComponent.MovieDetailsViewModelProvider,
    @Assisted private val movieId: Int
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> movieDetailsComponent.provideViewModel(movieId)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}