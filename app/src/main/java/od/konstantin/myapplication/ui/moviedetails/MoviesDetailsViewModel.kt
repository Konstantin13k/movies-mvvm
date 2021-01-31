package od.konstantin.myapplication.ui.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.MovieDetailsRepository
import od.konstantin.myapplication.data.models.MovieDetails

class MoviesDetailsViewModel @AssistedInject constructor(
    private val moviesRepository: MovieDetailsRepository,
    @Assisted private val movieId: Int,
) : ViewModel() {

    val movieDetails: Flow<MovieDetails?> get() = moviesRepository.observeMovieDetailsUpdates(movieId)

    init {
        viewModelScope.launch {
            moviesRepository.updateMovieData(movieId)
        }
    }
}