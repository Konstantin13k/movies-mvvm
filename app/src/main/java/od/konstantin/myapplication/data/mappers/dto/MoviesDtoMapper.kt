package od.konstantin.myapplication.data.mappers.dto

import od.konstantin.myapplication.data.mappers.Mapper
import od.konstantin.myapplication.utils.ImageSizes
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
private const val EMPTY_URL = ""
private const val MOVIE_RELEASE_DATE_FORMAT = "yyyy-MM-dd"

abstract class MoviesDtoMapper<T, V> : Mapper<T, V> {

    fun mapImageUrl(size: ImageSizes, endUrl: String?): String {
        return if (endUrl.isNullOrEmpty()) {
            EMPTY_URL
        } else {
            "${BASE_IMAGE_URL}$size/$endUrl"
        }
    }

    fun mapReleaseDate(dateString: String): Date? {
        return if (dateString.isNotEmpty()) {
            SimpleDateFormat(
                MOVIE_RELEASE_DATE_FORMAT,
                Locale.getDefault()
            ).parse(dateString)
        } else null
    }
}