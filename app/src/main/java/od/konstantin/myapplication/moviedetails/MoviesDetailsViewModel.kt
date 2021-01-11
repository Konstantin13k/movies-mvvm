package od.konstantin.myapplication.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.domain.MoviesDataSource
import od.konstantin.myapplication.utils.SingleLiveEvent

class MoviesDetailsViewModel(private val moviesDataSource: MoviesDataSource) : ViewModel() {

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() = _movieDetails

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            val movie = moviesDataSource.getMovie(movieId)
            if (movie != null) {
                _movieDetails.value = movie
            } //else show error
        }
    }

    private val _backToMoviesList = SingleLiveEvent<Boolean>()
    val backToMoviesList: LiveData<Boolean>
        get() = _backToMoviesList

    fun backButtonPressed() {
        _backToMoviesList.call()
    }
}