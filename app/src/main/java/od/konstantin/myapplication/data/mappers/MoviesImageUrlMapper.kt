package od.konstantin.myapplication.data.mappers

import od.konstantin.myapplication.utils.ImageSizes

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
private const val EMPTY_URL = ""

class MoviesImageUrlMapper {

    fun mapUrl(size: ImageSizes, endUrl: String?): String {
        return if (endUrl.isNullOrEmpty()) {
            EMPTY_URL
        } else {
            "$BASE_IMAGE_URL$size/$endUrl"
        }
    }
}