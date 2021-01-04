package od.konstantin.myapplication.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import od.konstantin.myapplication.R
import od.konstantin.myapplication.utils.ActorPictureSizes
import od.konstantin.myapplication.utils.BackdropSizes
import od.konstantin.myapplication.utils.ImageSizes
import od.konstantin.myapplication.utils.PosterSizes

private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/"

fun ImageView.setMoviePoster(poster: String, size: PosterSizes) {
    if (poster.isNotEmpty()) {
        Glide.with(context)
            .load(imageUrl(size, poster))
            .placeholder(R.drawable.small_poster_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .into(this)
    }
}

fun ImageView.setMovieBackdrop(backdrop: String, size: BackdropSizes) {
    setMoviePhoto(backdrop, size)
}

fun ImageView.setMovieActorPicture(picture: String, size: ActorPictureSizes) {
    setMoviePhoto(picture, size)
}

private fun ImageView.setMoviePhoto(endUrl: String, size: ImageSizes) {
    if (endUrl.isNotEmpty()) {
        Glide.with(context)
            .load(imageUrl(size, endUrl))
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .into(this)
    }
}

private fun imageUrl(size: ImageSizes, endUrl: String): String {
    return "$BASE_IMAGE_URL$size/$endUrl"
}