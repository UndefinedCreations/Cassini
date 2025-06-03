package com.undefined.cassini.element.item.async

import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Displays [fallbackItem] until the item has been fetched from the Kotlin `suspend` function, where it will then run [update].
 * If both [cartesianCoordinate] and [slot] are `null`, then it will attempt to use the next available slot.
 */
class SuspendAsyncItemElement(
    cartesianCoordinate: CartesianCoordinate? = null,
    slot: Int? = null,
    fallbackItem: (Player) -> ItemStack,
    val item: suspend (Player) -> ItemStack,
) : AsyncItemElement(cartesianCoordinate, slot, fallbackItem) {
    override fun getItem(player: Player): ItemStack {
        TODO("Call suspend function")
        return fallbackItem(player)
    }
}
