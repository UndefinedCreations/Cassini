package com.undefined.cassini.element

import com.undefined.cassini.data.item.ClickData

/**
 * Represents an element that can be clicked.
 */
abstract class ClickableElement : Element() {

    private val clickActions: MutableList<ClickData<*>.() -> Unit> = mutableListOf()

    /**
     * Adds a click action to [clickActions].
     */
    fun addAction(action: ClickData<*>.() -> Unit) {
        clickActions.add(action)
    }

    /**
     * Runs all click actions in this element with [data].
     */
    fun callActions(data: ClickData<*>) {
        for (action in clickActions) action(data)
    }

}