package od.konstantin.myapplication.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import od.konstantin.myapplication.utils.Event

fun <T> LiveData<Event<T>>.observeEvents(owner: LifecycleOwner, action: (T) -> Unit) {
    this.observe(owner, { event ->
        event.getContentIfNotHandled()?.let { action(it) }
    })
}