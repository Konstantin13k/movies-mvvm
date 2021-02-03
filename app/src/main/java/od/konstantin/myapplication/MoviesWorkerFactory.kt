package od.konstantin.myapplication

import androidx.work.DelegatingWorkerFactory
import od.konstantin.myapplication.data.MovieDetailsRepository
import od.konstantin.myapplication.ui.moviedetails.MovieDetailsWorker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesWorkerFactory @Inject constructor(
    movieDetailsRepository: MovieDetailsRepository
) : DelegatingWorkerFactory() {
    init {
        addFactory(MovieDetailsWorker.MovieDetailsWorkerFactory(movieDetailsRepository))
    }
}