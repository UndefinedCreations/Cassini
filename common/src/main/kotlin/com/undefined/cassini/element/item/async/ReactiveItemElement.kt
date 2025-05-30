package com.undefined.cassini.element.item.async

import reactor.core.publisher.Mono
import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents an item fetched from a [Mono] displayed in a [ItemMenu].
 * If both [cardinalSlot] and [slot] are `null`, then it will attempt to use the next available slot.
 */
class ReactiveItemElement(cardinalSlot: CartesianCoordinate? = null, slot: Int? = null, item: (Player) -> Mono<ItemStack>) : AsyncItemElement(cardinalSlot, slot)