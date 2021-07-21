package od.konstantin.myapplication.ui.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.FavoriteMoviesRepository
import od.konstantin.myapplication.data.models.FavoriteMovie
import javax.inject.Inject

class FavoriteMoviesViewModel @Inject constructor(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    private var memoryCache: List<FavoriteMovie> = emptyList()

    val favoriteMovies: Flow<List<FavoriteMovie>> = flow {
        emit(memoryCache)
        emitAll(favoriteMoviesRepository.observeFavoriteMoviesUpdates().onEach {
            memoryCache = it
        })
    }

    init {
        viewModelScope.launch {
            favoriteMoviesRepository.updateFavoriteMovies()
        }
    }

    fun unlikeMovie(movieId: Int) {
        viewModelScope.launch {
            favoriteMoviesRepository.setFavoriteMovie(movieId, false)
        }
    }
}