package com.undefined.cassini.container.item

import com.undefined.cassini.container.Container
import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ItemMenu

/**
 * Represents a container in an [ItemMenu]. Any null values are simply empty items.
 */
interface ItemContainer<T : ItemContainer<T>> : Container<T, ItemElement?>, FillOperations {

    override fun removeElement(row: Int, column: Int)
    override fun setElement(row: Int, column: Int, element: ItemElement)

    /**
     * Calculates a [CartesianCoordinate] from a [slot].
     */
    fun calculateCoordinateFromSlot(slot: Int): CartesianCoordinate

    // Implementations
    fun removeElement(slot: Int) {
        val coordinate = calculateCoordinateFromSlot(slot)
        removeElement(coordinate.x, coordinate.y)
    }

    fun setElement(slot: Int, element: ItemElement) {
        val coordinate = calculateCoordinateFromSlot(slot)
        setElement(coordinate.y, coordinate.x, element)
    }

}