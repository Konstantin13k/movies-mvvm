package od.konstantin.myapplication.data.mappers

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

private const val MOVIE_RELEASE_DATE_FORMAT = "yyyy-MM-dd"

class MoviesReleaseDateMapper @Inject constructor() {

    fun mapDate(dateString: String): Date? {
        return if (dateString.isNotEmpty()) {
            SimpleDateFormat(
                MOVIE_RELEASE_DATE_FORMAT,
                Locale.getDefault()
            ).parse(dateString)
        } else null
    }
}