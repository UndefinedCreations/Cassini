package com.undefined.cassini.element.item.async

import java.util.concurrent.CompletableFuture
import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Displays [fallbackItem] until the item has been fetched from a [CompletableFuture], where it will then run [update].
 * If both [cartesianCoordinate] and [slot] are `null`, then it will attempt to use the next available slot.
 */
class CompletableFutureItemElement(
    cartesianCoordinate: CartesianCoordinate? = null,
    slot: Int? = null,
    fallbackItem: (Player) -> ItemStack,
    val item: (Player) -> CompletableFuture<ItemStack>,
) : AsyncItemElement(cartesianCoordinate, slot, fallbackItem) {
    override fun getItem(player: Player): ItemStack {
        item(player).thenAccept { update() }.get()
        return fallbackItem(player)
    }
}