package od.konstantin.myapplication

import androidx.work.DelegatingWorkerFactory
import od.konstantin.myapplication.data.DiscoverMoviesRepository
import od.konstantin.myapplication.data.FavoriteMoviesRepository
import od.konstantin.myapplication.data.MovieDetailsRepository
import od.konstantin.myapplication.ui.moviedetails.MovieDetailsWorker
import od.konstantin.myapplication.ui.notifications.MovieNotifications
import od.konstantin.myapplication.ui.notifications.RecommendationMoviesWorker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesWorkerFactory @Inject constructor(
    movieDetailsRepository: MovieDetailsRepository,
    favoriteMoviesRepository: FavoriteMoviesRepository,
    discoverMoviesRepository: DiscoverMoviesRepository,
    movieNotifications: MovieNotifications,
) : DelegatingWorkerFactory() {
    init {
        addFactory(
            MovieDetailsWorker.MovieDetailsWorkerFactory(
                movieDetailsRepository,
            )
        )
        addFactory(
            RecommendationMoviesWorker.RecommendationMoviesWorkerFactory(
                favoriteMoviesRepository,
                discoverMoviesRepository,
                movieNotifications
            )
        )
    }
}