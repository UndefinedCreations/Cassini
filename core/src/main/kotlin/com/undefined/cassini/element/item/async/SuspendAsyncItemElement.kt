package com.undefined.cassini.element.item.async

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Displays [fallbackItem] until the item has been fetched from the Kotlin `suspend` function, where it will then run [update].
 */
class SuspendAsyncItemElement(
    fallbackItem: (Player) -> ItemStack,
    val item: suspend (Player) -> ItemStack,
) : AsyncItemElement(fallbackItem) {
    override fun getItem(player: Player): ItemStack {
        TODO("Call suspend function")
        return fallbackItem(player)
    }
}
