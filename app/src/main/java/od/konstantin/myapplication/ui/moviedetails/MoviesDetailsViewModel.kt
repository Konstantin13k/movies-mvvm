package od.konstantin.myapplication.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.data.models.MovieDetail

class MoviesDetailsViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetail>()
    val movieDetails: LiveData<MovieDetail>
        get() = _movieDetails

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            val movie = moviesRepository.getMovieDetail(movieId)
            if (movie != null) {
                _movieDetails.value = movie
            } //else show error
        }
    }
}