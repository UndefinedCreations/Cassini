package com.undefined.cassini.element.item.async

import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.item.AbstractItemElement
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

/**
 * Represents an item fetched from an async function displayed in a [ItemMenu].
 * If both [cardinalSlot] and [slot] are `null`, then it will attempt to use the next available slot.
 */
abstract class AsyncItemElement(cardinalSlot: CartesianCoordinate? = null, slot: Int? = null) : AbstractItemElement(cardinalSlot, slot) {
    companion object {
        // BASE
        fun suspend(cardinalSlot: CartesianCoordinate? = null, slot: Int? = null, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = SuspendAsyncItemElement(cardinalSlot, slot, item)
        fun suspend(x: Int, y: Int, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(CartesianCoordinate(x, y), null, item)
        fun suspend(slot: Int, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(null, slot, item)
        fun suspend(item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(0, 0, item)
        fun suspend(item: (Player) -> ItemStack): SuspendAsyncItemElement = suspend(0, 0, item)

        // BASE
        fun future(cardinalSlot: CartesianCoordinate? = null, slot: Int? = null, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = CompletableFutureItemElement(cardinalSlot, slot, item)
        fun future(x: Int, y: Int, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(CartesianCoordinate(x, y), null, item)
        fun future(slot: Int, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(null, slot, item)
        fun future(item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(0, 0, item)
        fun future(item: CompletableFuture<ItemStack>): CompletableFutureItemElement = future(0, 0) { item }

        // BASE
        fun reactive(cardinalSlot: CartesianCoordinate? = null, slot: Int? = null, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = ReactiveItemElement(cardinalSlot, slot, item)
        fun reactive(x: Int, y: Int, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(CartesianCoordinate(x, y), null, item)
        fun reactive(slot: Int, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(null, slot, item)
        fun reactive(item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(0, 0, item)
        fun reactive(item: Mono<ItemStack>): ReactiveItemElement = reactive(0, 0) { item }
    }
}