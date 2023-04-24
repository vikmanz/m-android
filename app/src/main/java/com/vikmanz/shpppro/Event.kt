package com.vikmanz.shpppro

//class Event<T>(
//    private val value: T
//) {
//    private var handled: Boolean = false
//
//    fun getValue(): T? {
//        if (handled) return null
//        handled = true
//        return value
//    }
//
//}

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * @see https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
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
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}