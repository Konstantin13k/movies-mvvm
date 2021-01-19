package od.konstantin.myapplication.data.mappers

import od.konstantin.myapplication.utils.ImageSizes
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
private const val EMPTY_URL = ""

@Singleton
class MoviesImageUrlMapper @Inject constructor() {

    fun mapUrl(size: ImageSizes, endUrl: String?): String {
        return if (endUrl.isNullOrEmpty()) {
            EMPTY_URL
        } else {
            "$BASE_IMAGE_URL$size/$endUrl"
        }
    }
}