package od.konstantin.myapplication.ui.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FavoriteMoviesViewModelFactory @Inject constructor(private val favoriteMoviesComponent: FavoriteMoviesComponent) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        FavoriteMoviesViewModel::class.java -> favoriteMoviesComponent.viewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}