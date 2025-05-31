package com.undefined.cassini.element.item.async

import com.undefined.cassini.element.CartesianCoordinate
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import reactor.core.publisher.Mono

/**
 * Displays [fallbackItem] until the item has been fetched from a [Mono], where it will then run [update].
 * If both [cardinalSlot] and [slot] are `null`, then it will attempt to use the next available slot.
 */
class ReactiveItemElement(
    cardinalSlot: CartesianCoordinate? = null,
    slot: Int? = null,
    fallbackItem: (Player) -> ItemStack,
    val item: (Player) -> Mono<ItemStack>,
) : AsyncItemElement(cardinalSlot, slot, fallbackItem) {
    override fun getItem(player: Player): ItemStack {
        item(player).subscribe { update() }
        return fallbackItem(player)
    }
}