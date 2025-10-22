package com.undefined.cassini.element.item.async

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.concurrent.CompletableFuture

/**
 * Displays [fallbackItem] until the item has been fetched from a [CompletableFuture], where it will then run [update].
 */
class CompletableFutureItemElement(
    fallbackItem: (Player) -> ItemStack,
    val item: (Player) -> CompletableFuture<ItemStack>,
) : AsyncItemElement(fallbackItem) {
    override fun getItem(player: Player): ItemStack {
        item(player).thenAccept { update() }.get()
        return fallbackItem(player)
    }
}