package od.konstantin.myapplication.ui.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import od.konstantin.myapplication.data.DiscoverMoviesRepository
import od.konstantin.myapplication.data.FavoriteMoviesRepository
import od.konstantin.myapplication.data.models.Genre
import od.konstantin.myapplication.data.models.SmallMoviePoster

class RecommendationMoviesWorker(
    appContext: Context,
    params: WorkerParameters,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    private val discoverMoviesRepository: DiscoverMoviesRepository,
    private val movieNotifications: MovieNotifications
) : CoroutineWorker(appContext, params) {

    companion object {
        private const val MAX_GENRES_LIST_SIZE = 3
    }

    override suspend fun doWork(): Result {
        return try {
            val favoriteMovies = favoriteMoviesRepository.getFavoriteMovies()
            val genres = favoriteMovies.map { it.genres }.popularGenres()
            val recommendedMovies =
                discoverMoviesRepository.getMoviesWithGenres(genres.map { it.id })
                    .filter { favoriteMovies.find { favoriteMovie -> favoriteMovie.movieId == it.movieId } == null }

            movieNotifications.showNotification(recommendedMovies.randomBestMovie())
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun List<List<Genre>>.popularGenres(): List<Genre> {
        val sortedGenreList = asSequence()
            .flatten()
            .groupBy { it.id }
            .toList()
            .sortedByDescending { it.second.size }

        return sortedGenreList.subList(0, MAX_GENRES_LIST_SIZE.coerceAtMost(sortedGenreList.size))
            .map { it.second.first() }
    }

    private fun List<SmallMoviePoster>.randomBestMovie(): SmallMoviePoster {
        return sortedByDescending { it.ratings }
            .subList(0, size / 2)
            .sortedByDescending { it.overview }
            .random()
    }

    class RecommendationMoviesWorkerFactory(
        private val favoriteMoviesRepository: FavoriteMoviesRepository,
        private val discoverMoviesRepository: DiscoverMoviesRepository,
        private val movieNotifications: MovieNotifications,
    ) :
        WorkerFactory() {

        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
            return when (workerClassName) {
                RecommendationMoviesWorker::class.java.name -> {
                    RecommendationMoviesWorker(
                        appContext,
                        workerParameters,
                        favoriteMoviesRepository,
                        discoverMoviesRepository,
                        movieNotifications
                    )
                }
                else -> null
            }
        }
    }
}