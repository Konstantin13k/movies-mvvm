package od.konstantin.myapplication

import android.app.Application
import androidx.work.*
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.components.DaggerAppComponent
import od.konstantin.myapplication.ui.moviedetails.MovieDetailsWorker
import od.konstantin.myapplication.ui.notifications.MovieNotifications
import od.konstantin.myapplication.ui.notifications.RecommendationMoviesWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val UPDATE_MOVIES_DELAY_HOURS = 8L
private const val UPDATE_MOVIES_WORK_NAME = "update_movies"
private const val RECOMMENDATION_MOVIES_DELAY_HOURS = 3L
private const val RECOMMENDATION_MOVIES_WORK_NAME = "recommendation_movies"

class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerConfiguration: Configuration

    @Inject
    lateinit var movieNotifications: MovieNotifications

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        runUpdateMoviesWorker()
        runRecommendationMoviesWorker()
    }

    private fun runUpdateMoviesWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true).build()

        val moviesUpdateRequest =
            OneTimeWorkRequestBuilder<MovieDetailsWorker>()
                .setConstraints(constraints)
                .setInitialDelay(UPDATE_MOVIES_DELAY_HOURS, TimeUnit.HOURS)
                .build()

        WorkManager.getInstance(applicationContext)
            .beginUniqueWork(
                UPDATE_MOVIES_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                moviesUpdateRequest
            ).enqueue()
    }

    private fun runRecommendationMoviesWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true).build()

        val moviesUpdateRequest =
            OneTimeWorkRequestBuilder<RecommendationMoviesWorker>()
                .setConstraints(constraints)
                .setInitialDelay(RECOMMENDATION_MOVIES_DELAY_HOURS, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(applicationContext)
            .beginUniqueWork(
                RECOMMENDATION_MOVIES_WORK_NAME,
                ExistingWorkPolicy.KEEP,
                moviesUpdateRequest
            ).enqueue()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }
}