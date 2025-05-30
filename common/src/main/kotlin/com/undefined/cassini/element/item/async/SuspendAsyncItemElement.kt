package com.undefined.cassini.element.item.async

import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents an item fetched from a suspending Kotlin function displayed in a [ItemMenu].
 * If both [cardinalSlot] and [slot] are `null`, then it will attempt to use the next available slot.
 */
class SuspendAsyncItemElement(cardinalSlot: CartesianCoordinate? = null, slot: Int? = null, val item: suspend (Player) -> ItemStack) : AsyncItemElement(cardinalSlot, slot)