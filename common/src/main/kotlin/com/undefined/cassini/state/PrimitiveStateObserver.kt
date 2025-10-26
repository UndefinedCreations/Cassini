package com.undefined.cassini.state

import kotlin.reflect.KProperty

/**
 * A [StateObserver] that is called each time the value is set.
 */
open class PrimitiveStateObserver<T : Any>(state: T) : StateObserver<T>(state) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = state

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        state = value
        callObservers()
    }

}