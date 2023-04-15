package od.konstantin.myapplication.ui.movieslist.page

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import od.konstantin.myapplication.data.FavoriteMoviesRepository
import od.konstantin.myapplication.data.MoviesRepository
import od.konstantin.myapplication.ui.movieslist.MoviesSortType

private const val KEY_SORT_TYPE = "sortType"

class MoviesListPageViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    @Assisted val savedStateHandle: SavedStateHandle,
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

        clearListCh.trySend(Unit)

        savedStateHandle[KEY_SORT_TYPE] = sortType
    }

    fun likeMovie(movieId: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMoviesRepository.setFavoriteMovie(movieId, isFavorite)
        }
    }

    private fun shouldShowMovies(sortType: MoviesSortType): Boolean {
        return savedStateHandle.get<MoviesSortType>(KEY_SORT_TYPE) != sortType
    }
}