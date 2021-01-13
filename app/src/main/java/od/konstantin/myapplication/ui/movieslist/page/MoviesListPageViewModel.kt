package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.ui.movieslist.MoviesSortType

class MoviesListPageViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    fun loadMovies(sortType: MoviesSortType): Flow<PagingData<MoviePoster>> {
        return moviesRepository.getMovies(sortType).cachedIn(viewModelScope)
    }
}