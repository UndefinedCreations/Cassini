package com.undefined.cassini.element.item

import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.Element
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents an element in an [ItemMenu]. If both [cardinalSlot] and [slot] are `null`, then it will attempt to use the next available slot.
 */
abstract class AbstractItemElement(val cardinalSlot: CartesianCoordinate? = null, val slot: Int? = null) : Element() {

    constructor(x: Int, y: Int) : this(CartesianCoordinate(x, y))
    constructor(slot: Int) : this(null, slot)
    constructor(slot: Int, x: Int, y: Int) : this(CartesianCoordinate(x, y), slot)

    abstract fun getItem(player: Player): ItemStack

}