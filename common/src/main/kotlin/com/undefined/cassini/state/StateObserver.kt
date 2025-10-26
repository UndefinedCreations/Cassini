@file:JvmName("State")
package com.undefined.cassini.state

/**
 * Represents a property that is observable.
 */
abstract class StateObserver<T : Any>(var state: T) {

    private val observers: MutableList<(T) -> Unit> = mutableListOf()

    /**
     * Run the [observer] block anytime the state changes.
     */
    fun observe(observer: (T) -> Unit) {
        observers.add(observer)
    }

    /**
     * Calls, and runs all [observers].
     */
    internal fun callObservers() {
        for (block in observers) block(state)
    }

}