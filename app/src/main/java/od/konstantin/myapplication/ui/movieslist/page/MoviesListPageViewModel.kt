package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.ui.movieslist.MoviesSortType

private const val KEY_SORT_TYPE = "sortType"

class MoviesListPageViewModel(
    private val moviesRepository: MoviesRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movies = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        savedStateHandle.getLiveData<MoviesSortType>(KEY_SORT_TYPE)
            .asFlow()
            .flatMapLatest { moviesRepository.getMovies(it) }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    fun loadMovies(sortType: MoviesSortType) {
        if (!shouldShowMovies(sortType)) return

        clearListCh.offer(Unit)

        savedStateHandle.set(KEY_SORT_TYPE, sortType)
    }

    private fun shouldShowMovies(sortType: MoviesSortType): Boolean {
        return savedStateHandle.get<MoviesSortType>(KEY_SORT_TYPE) != sortType
    }
}