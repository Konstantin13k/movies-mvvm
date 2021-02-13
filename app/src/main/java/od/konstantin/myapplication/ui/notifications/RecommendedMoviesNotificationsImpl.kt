package od.konstantin.myapplication.ui.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import od.konstantin.myapplication.R
import od.konstantin.myapplication.data.models.SmallMoviePoster
import od.konstantin.myapplication.ui.MainActivity

private const val BASE_CONTENT_URI = "https://www.themoviedb.org/movie/"

interface MovieNotifications {
    fun initialize()
    fun showNotification(moviePoster: SmallMoviePoster)
    fun dismissNotification(movieId: Int)
}

class RecommendedMoviesNotificationsImpl(private val context: Context) :
    MovieNotifications {

    companion object {
        private const val CHANNEL_NEW_MOVIES = "new_movies"

        private const val REQUEST_CONTENT = 1

        private const val MOVIE_TAG = "movie"
    }

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {
            val notificationChannel = NotificationChannelCompat.Builder(
                CHANNEL_NEW_MOVIES, NotificationManagerCompat.IMPORTANCE_HIGH
            ).setName(context.getString(R.string.channel_movie_recommendation))
                .build()
            notificationManagerCompat.createNotificationChannel(notificationChannel)
        }
    }

    @WorkerThread
    override fun showNotification(moviePoster: SmallMoviePoster) {
        val contentUri = "$BASE_CONTENT_URI${moviePoster.movieId}".toUri()
        val movieDetailsIntent = Intent(context, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setData(contentUri)

        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CONTENT,
            movieDetailsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIES)
            .setContentTitle(moviePoster.title)
            .setSmallIcon(R.drawable.ic_movies)
            .setCategory(Notification.CATEGORY_RECOMMENDATION)
            .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(moviePoster.overview))
            .build()

        notificationManagerCompat.notify(MOVIE_TAG, moviePoster.movieId, notification)
    }

    override fun dismissNotification(movieId: Int) {
        notificationManagerCompat.cancel(MOVIE_TAG, movieId)
    }
}