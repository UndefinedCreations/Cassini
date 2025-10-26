package com.undefined.cassini.state

import kotlin.reflect.KProperty

/**
 * A [StateObserver] that is called each time the value is set, when the [update] method is called.
 */
open class ObjectStateObserver<T : Any>(state: T) : StateObserver<T>(state) {

    fun update(block: (T) -> Unit) {
        block(state)
        callObservers()
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = state

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        state = value
        callObservers()
    }

}