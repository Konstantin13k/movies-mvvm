package od.konstantin.myapplication.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.MovieDetailsRepository
import od.konstantin.myapplication.data.models.MovieDetail
import javax.inject.Inject

class MoviesDetailsViewModel @Inject constructor(private val moviesRepository: MovieDetailsRepository) :
    ViewModel() {

    fun loadMovie(movieId: Int): LiveData<MovieDetail> {
        viewModelScope.launch {
            moviesRepository.updateMovieData(movieId)
        }
        return moviesRepository.getMovieDetail(movieId)
    }
}