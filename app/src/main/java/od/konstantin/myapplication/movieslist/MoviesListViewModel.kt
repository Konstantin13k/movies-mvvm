package od.konstantin.myapplication.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.data.models.MoviePoster

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _sortType = MutableLiveData<MoviesSortType>(MoviesSortType.NowPlaying)
    val sortType: LiveData<MoviesSortType>
        get() = _sortType

    fun changeSortType(newSortTypeId: Int) {
        val newSortType = MoviesSortType.getSortType(newSortTypeId)
        if (newSortType != _sortType.value) {
            _sortType.value = newSortType
        }
    }

    fun loadMovies(sortType: MoviesSortType): Flow<PagingData<MoviePoster>> {
        return moviesRepository.getMovies(sortType).cachedIn(viewModelScope)
    }

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