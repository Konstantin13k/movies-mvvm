package od.konstantin.myapplication.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import od.konstantin.myapplication.data.models.MoviePoster
import od.konstantin.myapplication.data.remote.MoviesApi
import od.konstantin.myapplication.data.remote.MoviesPagingSource
import od.konstantin.myapplication.data.remote.models.JsonMovie

class MoviesRepository(private val moviesApi: MoviesApi) {

    fun getMovies(): Flow<PagingData<MoviePoster>> {
        return Pager(PagingConfig(pageSize = 6)) {
            MoviesPagingSource(moviesApi)
        }.flow
    }


}