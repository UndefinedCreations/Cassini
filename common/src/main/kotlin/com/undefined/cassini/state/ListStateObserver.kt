package com.undefined.cassini.state

import java.util.function.Predicate

/**
 * A [StateObserver] that is called each time the value is set and an element in the list is added or removed.
 */
class ListStateObserver<T : Any>(state: MutableCollection<T>) : PrimitiveStateObserver<MutableCollection<T>>(state), MutableCollection<T> by state {

    override fun add(element: T): Boolean = state.add(element).apply { callObservers() }
    override fun addAll(elements: Collection<T>): Boolean = state.addAll(elements).apply { callObservers() }
    override fun remove(element: T): Boolean = state.remove(element).apply { callObservers() }
    override fun removeAll(elements: Collection<T>): Boolean = state.removeAll(elements).apply { callObservers() }
    override fun retainAll(elements: Collection<T>): Boolean = state.retainAll(elements).apply { callObservers() }
    override fun removeIf(filter: Predicate<in T>): Boolean = state.removeIf(filter).apply { callObservers() }

}