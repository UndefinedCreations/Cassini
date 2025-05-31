package com.undefined.cassini.container

import com.undefined.cassini.element.Element
import com.undefined.cassini.state.ListStateObserver
import com.undefined.cassini.state.ObjectStateObserver
import com.undefined.cassini.state.PrimitiveStateObserver

/**
 * Contains a list of elements and other containers.
 *
 * @param C The extending [Container].
 * @param E [Element] that can be added.
 */
abstract class Container<C : Container<C, *>, E : Element> {

    private val elements: MutableList<E> = mutableListOf()
    private val containers: MutableList<C> = mutableListOf()

    fun addElement(element: E) {
        this.elements.add(element)
        element.containers.add(this)
    }

    fun addContainer(container: C) {
        this.containers.add(container)
    }

    fun <T : Any> state(initialState: T): PrimitiveStateObserver<T> =
        PrimitiveStateObserver(initialState).apply { observe { update() } }

    fun <T : Any> listState(initialState: MutableCollection<T>): ListStateObserver<T> =
        ListStateObserver(initialState).apply { observe { update() } }

    fun <T : Any> objectState(initialState: T): ObjectStateObserver<T> =
        ObjectStateObserver(initialState).apply { observe { update() } }

    /**
     * Updates the contents of this container to the user.
     */
    fun update() {
        TODO()
    }

}