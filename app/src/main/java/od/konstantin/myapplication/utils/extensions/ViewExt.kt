package od.konstantin.myapplication.utils.extensions

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import java.text.SimpleDateFormat
import java.util.*

fun View.show() {
    isVisible = true
}

fun View.hide() {
    isVisible = false
}

fun TextView.setDateOrHide(date: Date?, dateFormat: String) {
    if (date != null) {
        setDate(date, dateFormat)
    } else {
        hide()
    }
}

fun TextView.setDate(date: Date, dateFormat: String) {
    text = SimpleDateFormat(dateFormat, Locale.getDefault()).format(date)
}
