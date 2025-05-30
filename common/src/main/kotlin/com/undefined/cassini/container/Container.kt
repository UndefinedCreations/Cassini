package com.undefined.cassini.container

import com.undefined.cassini.element.Element

/**
 * Contains a list of elements and other containers.
 *
 * @param C The extending [Container].
 * @param E [Element] that can be added.
 */
abstract class Container<C : Container<C, *>, E : Element> {

    val elements: MutableList<E> = mutableListOf()
        private set
    val containers: MutableList<C> = mutableListOf()
        private set

    fun addElement(element: E) {
        this.elements.add(element)
    }

    fun addContainer(container: C) {
        this.containers.add(container)
    }

}