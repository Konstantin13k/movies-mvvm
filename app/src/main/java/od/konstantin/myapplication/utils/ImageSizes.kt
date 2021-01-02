package od.konstantin.myapplication.utils

abstract class ImageSizes(private val size: String) {
    override fun toString(): String = size
}

sealed class PosterSizes(size: String) : ImageSizes(size) {
    object W92 : PosterSizes("w92")
    object W154 : PosterSizes("w154")
    object W185 : PosterSizes("w185")
    object W300 : PosterSizes("w300")
    object W500 : PosterSizes("w500")
    object W780 : PosterSizes("w780")
    object ORIGINAL : PosterSizes("original")
}

sealed class BackdropSizes(size: String) : ImageSizes(size) {
    object W300 : BackdropSizes("w300")
    object W780 : BackdropSizes("w780")
    object W1280 : BackdropSizes("w1280")
    object ORIGINAL : BackdropSizes("original")
}