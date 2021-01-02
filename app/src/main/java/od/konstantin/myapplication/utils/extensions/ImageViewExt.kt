package od.konstantin.myapplication.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import od.konstantin.myapplication.R
import od.konstantin.myapplication.utils.ImageSizes
import od.konstantin.myapplication.utils.PosterSizes

private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/"

fun ImageView.setMoviePoster(poster: String, size: PosterSizes) {
    if (poster.isNotEmpty()) {
        Glide.with(context)
            .load(imageUrl(size, poster))
            .placeholder(R.drawable.small_poster_placeholder)
            .into(this)
    }
}

private fun imageUrl(size: ImageSizes, endUrl: String): String {
    return "$BASE_IMAGE_URL$size/$endUrl"
}