package od.konstantin.myapplication.ui.common.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalListItemDecorator(private val innerMargin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {

            if (parent.getChildAdapterPosition(view) != 0) {
                left = innerMargin
            }
        }
    }
}