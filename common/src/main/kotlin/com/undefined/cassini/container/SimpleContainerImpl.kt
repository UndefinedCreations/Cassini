package com.undefined.cassini.container

import com.undefined.cassini.element.Element

/**
 * A simple implementation of [Container].
 *
 * @param C The extending [Container].
 * @param E [Element] that can be added.
 */
abstract class SimpleContainerImpl<C : Container<C, *>, E : Element?> : Container<C, E> {

    protected val elements: MutableList<E> = mutableListOf()
    protected val containers: MutableList<C> = mutableListOf()

    override fun addElement(element: E & Any) {
        this.elements.add(element)
        element.containers.add(this)
    }

    override fun addContainer(container: C) {
        this.containers.add(container)
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    /**
     * Gets all elements, including from sub-containers.
     */
    @Suppress("UNCHECKED_CAST")
    override fun getAllElements(): List<E> = (elements + containers.flatMap { it.getAllElements() }) as List<E>

}