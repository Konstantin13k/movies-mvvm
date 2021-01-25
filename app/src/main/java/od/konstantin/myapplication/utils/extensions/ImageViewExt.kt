package od.konstantin.myapplication.utils.extensions

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