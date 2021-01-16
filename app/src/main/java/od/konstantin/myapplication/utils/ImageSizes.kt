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

sealed class ActorPictureSizes(size: String) : ImageSizes(size) {
    object W45 : ActorPictureSizes("w45")
    object W185 : ActorPictureSizes("w185")
    object H632 : ActorPictureSizes("h632")
    object ORIGINAL : ActorPictureSizes("original")
}