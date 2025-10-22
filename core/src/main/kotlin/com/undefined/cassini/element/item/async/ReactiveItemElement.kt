package com.undefined.cassini.element.item.async

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import reactor.core.publisher.Mono

/**
 * Displays [fallbackItem] until the item has been fetched from a [Mono], where it will then run [update].
 */
class ReactiveItemElement(
    fallbackItem: (Player) -> ItemStack,
    val item: (Player) -> Mono<ItemStack>,
) : AsyncItemElement(fallbackItem) {
    override fun getItem(player: Player): ItemStack {
        item(player).subscribe { update() }
        return fallbackItem(player)
    }
}