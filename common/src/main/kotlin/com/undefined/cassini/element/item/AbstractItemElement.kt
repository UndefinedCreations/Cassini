package com.undefined.cassini.element.item

import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.Element
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents an element in an [ItemMenu]. If both [cartesianCoordinate] and [slot] are `null`, then it will attempt to use the next available slot.
 */
abstract class AbstractItemElement(val cartesianCoordinate: CartesianCoordinate? = null, val slot: Int? = null) : Element() {

    constructor(x: Int, y: Int) : this(CartesianCoordinate(x, y))
    constructor(slot: Int) : this(null, slot)
    constructor(slot: Int, x: Int, y: Int) : this(CartesianCoordinate(x, y), slot)

    /**
     * Computes the slot in standard form used in Minecraft container menus.
     *
     * @param inventoryYSlots The amount of slots the inventory has on the y-axis, starting from 1.
     */
    fun computeSlot(inventoryYSlots: Int): Int {
        if (cartesianCoordinate != null) return ((cartesianCoordinate.x) * ((cartesianCoordinate.y) * inventoryYSlots))
        if (slot != null) return slot
        throw IllegalArgumentException("Cartesian coordinate and slot cannot both be null!")
    }

    abstract fun getItem(player: Player): ItemStack

}