package od.konstantin.myapplication.ui.movieslist.page

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

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

        val spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
        val isLeftCard = spanIndex == 0
        val isRightCard = !isLeftCard

        with(outRect) {
            top = innerMargin
            bottom = innerMargin
            left = if (isLeftCard) outerMargin else innerMargin
            right = if (isRightCard) outerMargin else innerMargin
        }
    }
}