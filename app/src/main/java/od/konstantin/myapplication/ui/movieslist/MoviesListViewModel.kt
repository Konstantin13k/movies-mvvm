package od.konstantin.myapplication.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoviesListViewModel : ViewModel() {

    private val _selectedMovie = MutableLiveData<Int?>()
    val selectedMovie: LiveData<Int?>
        get() = _selectedMovie

    fun selectMovie(movieId: Int) {
        _selectedMovie.value = movieId
    }

    fun showMovieDetailsDone() {
        _selectedMovie.value = null
    }
}