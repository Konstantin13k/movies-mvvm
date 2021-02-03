package od.konstantin.myapplication

import android.annotation.SuppressLint
import android.app.Application
import androidx.work.*
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.components.DaggerAppComponent
import od.konstantin.myapplication.ui.moviedetails.MovieDetailsWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val UPDATE_MOVIES_DELAY = 8L
private const val UPDATE_MOVIES_WORK_NAME = "update_movies"

class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerConfiguration: Configuration

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        runUpdateMoviesWorker()
    }

    @SuppressLint("EnqueueWork")
    private fun runUpdateMoviesWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true).build()

        val moviesUpdateRequest =
            OneTimeWorkRequestBuilder<MovieDetailsWorker>()
                .setConstraints(constraints)
                .setInitialDelay(UPDATE_MOVIES_DELAY, TimeUnit.HOURS)
                .build()

        WorkManager.getInstance(applicationContext)
            .beginUniqueWork(UPDATE_MOVIES_WORK_NAME, ExistingWorkPolicy.REPLACE, moviesUpdateRequest)
            .enqueue()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }
}