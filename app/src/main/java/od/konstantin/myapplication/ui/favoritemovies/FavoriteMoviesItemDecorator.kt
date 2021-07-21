package od.konstantin.myapplication.ui.favoritemovies

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FavoriteMoviesItemDecorator(
    private val innerMargin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewHolder = parent.getChildViewHolder(view)
        val currentPosition =
            parent.getChildAdapterPosition(view).takeIf { it != RecyclerView.NO_POSITION }
                ?: viewHolder.oldPosition

        if (currentPosition != 0) {
            outRect.top = innerMargin
        }
    }
}