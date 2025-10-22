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
interface Container<C : Container<C, *>, E : Element?> {

    fun addElement(element: E & Any)
    fun addContainer(container: C)

    /**
     * Updates the contents of this container to the user.
     */
    fun update()

    fun getAllElements(): List<E>

    fun <T : Any> state(initialState: T): PrimitiveStateObserver<T> =
        PrimitiveStateObserver(initialState).apply { observe { update() } }

    fun <T : Any> listState(initialState: MutableCollection<T>): ListStateObserver<T> =
        ListStateObserver(initialState).apply { observe { update() } }

    fun <T : Any> objectState(initialState: T): ObjectStateObserver<T> =
        ObjectStateObserver(initialState).apply { observe { update() } }

}