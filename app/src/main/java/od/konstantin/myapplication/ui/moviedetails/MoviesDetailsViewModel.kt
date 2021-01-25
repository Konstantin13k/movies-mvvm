package od.konstantin.myapplication.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.MovieDetailsRepository
import od.konstantin.myapplication.data.models.MovieDetail

class MoviesDetailsViewModel @AssistedInject constructor(
    private val moviesRepository: MovieDetailsRepository,
    @Assisted private val movieId: Int,
) : ViewModel() {

    val movieDetails: LiveData<MovieDetail> = moviesRepository.getMovieDetail(movieId)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.updateMovieData(movieId)
        }
    }
}