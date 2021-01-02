package od.konstantin.myapplication.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.data.models.Movie
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.models.JsonMovie
import od.konstantin.myapplication.domain.MoviesDataSource

class MoviesListViewModel(
    private val moviesDataSource: MoviesDataSource,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun loadPagingMovies(): Flow<PagingData<MoviePoster>> {
        return moviesRepository.getMovies().cachedIn(viewModelScope)
    }

    fun loadMovies() {
        viewModelScope.launch {
           /* val movies = moviesDataSource.getMovies()
            _movies.value = movies*/

        }
    }

    private val _selectedMovie = MutableLiveData<Movie?>()
    val selectedMovie: LiveData<Movie?>
        get() = _selectedMovie

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun showMovieDetailsDone() {
        _selectedMovie.value = null
    }
}