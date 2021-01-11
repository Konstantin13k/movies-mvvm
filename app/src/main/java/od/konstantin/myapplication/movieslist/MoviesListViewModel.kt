package od.konstantin.myapplication.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.domain.MoviesDataSource
import od.konstantin.myapplication.utils.SingleLiveEvent

class MoviesListViewModel(private val moviesDataSource: MoviesDataSource) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun loadMovies() {
        viewModelScope.launch {
            val movies = moviesDataSource.getMovies()
            _movies.value = movies
        }
    }

    private val _selectedMovie = SingleLiveEvent<Movie?>()
    val selectedMovie: LiveData<Movie?>
        get() = _selectedMovie

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}