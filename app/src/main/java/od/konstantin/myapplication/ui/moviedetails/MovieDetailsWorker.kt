package od.konstantin.myapplication.ui.moviedetails

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import od.konstantin.myapplication.data.MovieDetailsRepository

class MovieDetailsWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val movieDetailsRepository: MovieDetailsRepository
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            movieDetailsRepository.updateMoviesDetails()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    class MovieDetailsWorkerFactory(private val movieDetailsRepository: MovieDetailsRepository) :
        WorkerFactory() {

        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
            return when (workerClassName) {
                MovieDetailsWorker::class.java.name -> {
                    MovieDetailsWorker(appContext, workerParameters, movieDetailsRepository)
                }
                else -> null
            }
        }
    }
}