package od.konstantin.myapplication.utils

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Return the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, event if it's already been handled
     */
    fun peekContent(): T = content
}