package od.konstantin.myapplication.utils.extensions

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import od.konstantin.myapplication.R

fun ImageView.setImg(imgUrl: String, transitionDuration: Int = 300) {
    if (imgUrl.isNotEmpty()) {
        Glide.with(context)
            .load(imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade(transitionDuration))
            .into(this)
    }
}

fun ImageView.setLike(isLiked: Boolean) {
    Glide.with(context).load(
        if (isLiked) {
            R.drawable.ic_color_like
        } else {
            R.drawable.ic_like
        }
    ).into(this)
}

private const val BLACK_AND_WHITE_SATURATION = 0f

fun ImageView.setBlackAndWhiteEffect() {
    setSaturation(BLACK_AND_WHITE_SATURATION)
}

fun ImageView.setSaturation(saturation: Float) {
    colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(saturation) })
}