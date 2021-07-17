package od.konstantin.myapplication.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.FavoriteMoviesRepository
import od.konstantin.myapplication.data.MovieDetailsRepository
import od.konstantin.myapplication.data.models.MovieDetails

class MoviesDetailsViewModel @AssistedInject constructor(
    private val moviesRepository: MovieDetailsRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    @Assisted private val movieId: Int,
) : ViewModel() {

    private val _movieDetailsState = MutableStateFlow(MovieDetailsState())
    val movieDetailsState: StateFlow<MovieDetailsState> = _movieDetailsState.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.observeMovieDetailsUpdates(movieId)
                .filterNotNull()
                .collect { movieDetails ->
                    _movieDetailsState.emit(
                        _movieDetailsState.value.copy(
                            movieDetails = movieDetails,
                            isLoading = false
                        )
                    )
                }
        }
        viewModelScope.launch {
            favoriteMoviesRepository.observeFavoriteMovieUpdates(movieId).collect { isFavorite ->
                _movieDetailsState.emit(_movieDetailsState.value.copy(isFavorite = isFavorite))
            }
        }
        updateMovieData()
    }

    fun changeFavoriteMovie(isFavorite: Boolean) {
        viewModelScope.launch {
            favoriteMoviesRepository.setFavoriteMovie(movieId, isFavorite)
        }
    }

    fun updateMovieData() {
        viewModelScope.launch {
            _movieDetailsState.emit(_movieDetailsState.value.copy(isLoading = true))
            moviesRepository.updateMovieData(movieId)
        }
    }

    data class MovieDetailsState(
        val movieDetails: MovieDetails? = null,
        val isFavorite: Boolean = false,
        val isLoading: Boolean = false
    )
}