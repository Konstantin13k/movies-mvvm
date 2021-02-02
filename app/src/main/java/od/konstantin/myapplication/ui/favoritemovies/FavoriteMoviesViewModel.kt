package od.konstantin.myapplication.ui.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.FavoriteMoviesRepository
import javax.inject.Inject

class FavoriteMoviesViewModel @Inject constructor(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    val favoriteMovies get() = favoriteMoviesRepository.observeFavoriteMoviesUpdates()

    init {
        viewModelScope.launch {
            favoriteMoviesRepository.updateFavoriteMovies()
        }
    }
}