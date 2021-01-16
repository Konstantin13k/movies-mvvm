package od.konstantin.myapplication.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.setImg(imgUrl: String, transitionDuration: Int = 300) {
    if (imgUrl.isNotEmpty()) {
        Glide.with(context)
            .load(imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade(transitionDuration))
            .into(this)
    }
}