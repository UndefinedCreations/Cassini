package com.undefined.cassini.element

import com.undefined.cassini.container.Container

/**
 * Represents an element to be displayed on the screen.
 */
abstract class Element() {

    /**
     * A list of containers where this element is used.
     * These containers will be updated on [update].
     */
    private val containers: MutableList<Container<*, *>> = mutableListOf()

    /**
     * Adds [container] to [containers].
     */
    fun addContainer(container: Container<*, *>) {
        containers.add(container)
    }

    /**
     * This will update all containers using this element.
     */
    fun update() {
        for (container in containers) container.update()
    }

}