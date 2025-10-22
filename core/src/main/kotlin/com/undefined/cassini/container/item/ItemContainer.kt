package com.undefined.cassini.container.item

import com.undefined.cassini.container.Container
import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.item.ItemElement

/**
 * Represents a container in an ItemMenu.
 */
class ItemContainer(
    val cartesianCoordinate: CartesianCoordinate? = null,
    val slot: Int? = null,
    val width: Int,
    val height: Int,
) : Container<ItemContainer, ItemElement>() {
    constructor(x: Int, y: Int, width: Int, height: Int) : this(CartesianCoordinate(x, y), null, width, height)
    constructor(slot: Int, width: Int, height: Int) : this(null, slot, width, height)
}