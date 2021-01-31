package od.konstantin.myapplication

import android.app.Application
import androidx.work.*
import od.konstantin.myapplication.di.components.AppComponent
import od.konstantin.myapplication.di.components.DaggerAppComponent
import od.konstantin.myapplication.ui.moviedetails.MovieDetailsWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerConfiguration: Configuration

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        // Не совсем понял при каких условиях нужно запускать воркер.
        // И поэтому запускаю при старте приложения.
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true).build()

        val moviesUpdateRequest =
            OneTimeWorkRequestBuilder<MovieDetailsWorker>()
                .setConstraints(constraints)
                .setInitialDelay(8L, TimeUnit.HOURS)
                .build()

        WorkManager.getInstance(applicationContext).enqueue(moviesUpdateRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }
}