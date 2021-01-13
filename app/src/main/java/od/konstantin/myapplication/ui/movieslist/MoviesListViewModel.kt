package od.konstantin.myapplication.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import od.konstantin.myapplication.utils.Event

class MoviesListViewModel : ViewModel() {

    private val _selectedMovie = MutableLiveData<Event<Int>>()
    val selectedMovie: LiveData<Event<Int>>
        get() = _selectedMovie

    fun selectMovie(movieId: Int) {
        _selectedMovie.value = Event(movieId)
    }
}