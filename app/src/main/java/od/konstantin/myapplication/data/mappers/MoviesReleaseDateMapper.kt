package od.konstantin.myapplication.data.mappers

import java.text.SimpleDateFormat
import java.util.*

private const val MOVIE_RELEASE_DATE_FORMAT = "yyyy-MM-dd"

class MoviesReleaseDateMapper {

    fun mapDate(dateString: String): Date? {
        return if (dateString.isNotEmpty()) {
            SimpleDateFormat(
                MOVIE_RELEASE_DATE_FORMAT,
                Locale.getDefault()
            ).parse(dateString)
        } else null
    }
}