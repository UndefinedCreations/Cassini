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
    fun getAllElements(): List<E>

    /**
     * Update this container for all its viewers.
     */
    fun update()
}