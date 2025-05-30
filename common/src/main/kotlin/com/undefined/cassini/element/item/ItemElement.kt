package com.undefined.cassini.element.item

import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.Element
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents an item to be displayed in a [ItemMenu]. If both [cardinalSlot] and [slot] are `null`, then it will attempt to use the next available slot.
 */
class ItemElement private constructor(cardinalSlot: CartesianCoordinate? = null, slot: Int? = null, val item: (Player) -> ItemStack) : AbstractItemElement(cardinalSlot, slot) {
    constructor(x: Int, y: Int, item: (Player) -> ItemStack) : this(CartesianCoordinate(x, y), null, item)
    constructor(slot: Int, item: (Player) -> ItemStack) : this(null, slot, item)
    constructor(item: (Player) -> ItemStack) : this(0, 0, item)
    constructor(item: ItemStack) : this(0, 0, { item })
}