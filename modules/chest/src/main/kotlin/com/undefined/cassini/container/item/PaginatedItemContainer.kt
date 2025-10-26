package com.undefined.cassini.container.item

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.PaginatedChestMenu

/**
 * Represents a paginated container in a [PaginatedChestMenu]. Any null values are simply empty items.
 */
interface PaginatedItemContainer {

    /**
     * Adds a number of paginated elements.
     */
    fun addPaginatedElements(elements: List<ItemElement>)

    /**
     * Adds a number of paginated elements.
     */
    fun addPaginatedElements(vararg elements: ItemElement) = addPaginatedElements(elements.toList())

    /**
     * Adds a paginated element.
     */
    fun addPaginatedElement(element: ItemElement) = addPaginatedElements(element)

}