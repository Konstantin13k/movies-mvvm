package od.konstantin.myapplication.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import od.konstantin.myapplication.ui.notifications.MovieNotifications
import od.konstantin.myapplication.ui.notifications.RecommendedMoviesNotificationsImpl
import javax.inject.Singleton

@Module
class MoviesNotificationsModule {

    @Singleton
    @Provides
    fun provideMoviesNotification(context: Context): MovieNotifications {
        return RecommendedMoviesNotificationsImpl(context)
    }
}