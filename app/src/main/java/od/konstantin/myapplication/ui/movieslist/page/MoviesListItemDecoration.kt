package od.konstantin.myapplication.ui.movieslist.page

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MoviesListItemDecoration(
    private val innerMargin: Int,
    private val outerMargin: Int
) :
    RecyclerView.ItemDecoration() {

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

        val isLeftCard = currentPosition % 2 == 0
        val isRightCard = !isLeftCard

        with(outRect) {
            top = innerMargin
            bottom = innerMargin
            left = if (isLeftCard) outerMargin else innerMargin
            right = if (isRightCard) outerMargin else innerMargin
        }
    }
}