package com.undefined.cassini.container.item

import com.undefined.cassini.container.Container
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ItemMenu

/**
 * Represents a container in an [ItemMenu]. Any null values are simply empty items.
 */
interface ItemContainer<T : ItemContainer<T>> : Container<T, ItemElement?> {
    fun removeElement(slot: Int)
    fun setElement(slot: Int, element: ItemElement)
}