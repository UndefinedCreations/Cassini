package com.undefined.cassini.element.item.async

import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.item.AbstractItemElement
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

/**
 * Displays [fallbackItem] until an async item has been computed, where it will then run [update].
 * If both [cartesianCoordinate] and [slot] are `null`, then it will attempt to use the next available slot.
 */
abstract class AsyncItemElement(cartesianCoordinate: CartesianCoordinate? = null, slot: Int? = null, val fallbackItem: (Player) -> ItemStack) : AbstractItemElement(cartesianCoordinate, slot) {
    companion object {
        // BASE
        fun suspend(cartesianCoordinate: CartesianCoordinate? = null, slot: Int? = null, fallbackItem: (Player) -> ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = SuspendAsyncItemElement(cartesianCoordinate, slot, fallbackItem, item)
        fun suspend(x: Int, y: Int, fallbackItem: (Player) -> ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(CartesianCoordinate(x, y), null, fallbackItem, item)
        fun suspend(slot: Int, fallbackItem: (Player) -> ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(null, slot, fallbackItem, item)
        fun suspend(fallbackItem: (Player) -> ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(0, 0, fallbackItem, item)
        fun suspend(fallbackItem: (Player) -> ItemStack, item: (Player) -> ItemStack): SuspendAsyncItemElement = suspend(0, 0, fallbackItem, item)

        fun suspend(cartesianCoordinate: CartesianCoordinate? = null, slot: Int? = null, fallbackItem: ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = SuspendAsyncItemElement(cartesianCoordinate, slot, { fallbackItem }, item)
        fun suspend(x: Int, y: Int, fallbackItem: ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(CartesianCoordinate(x, y), null, fallbackItem, item)
        fun suspend(slot: Int, fallbackItem: ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(null, slot, fallbackItem, item)
        fun suspend(fallbackItem: ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend(0, 0, fallbackItem, item)
        fun suspend(fallbackItem: ItemStack, item: (Player) -> ItemStack): SuspendAsyncItemElement = suspend(0, 0, fallbackItem, item)

        // BASE
        fun future(cartesianCoordinate: CartesianCoordinate? = null, slot: Int? = null, fallbackItem: (Player) -> ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = CompletableFutureItemElement(cartesianCoordinate, slot, fallbackItem, item)
        fun future(x: Int, y: Int, fallbackItem: (Player) -> ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(CartesianCoordinate(x, y), null, fallbackItem, item)
        fun future(slot: Int, fallbackItem: (Player) -> ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(null, slot, fallbackItem, item)
        fun future(fallbackItem: (Player) -> ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(0, 0, fallbackItem, item)
        fun future(fallbackItem: (Player) -> ItemStack, item: CompletableFuture<ItemStack>): CompletableFutureItemElement = future(0, 0, fallbackItem) { item }

        fun future(cartesianCoordinate: CartesianCoordinate? = null, slot: Int? = null, fallbackItem: ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = CompletableFutureItemElement(cartesianCoordinate, slot, { fallbackItem }, item)
        fun future(x: Int, y: Int, fallbackItem: ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(CartesianCoordinate(x, y), null, fallbackItem, item)
        fun future(slot: Int, fallbackItem: ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(null, slot, fallbackItem, item)
        fun future(fallbackItem: ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future(0, 0, fallbackItem, item)
        fun future(fallbackItem: ItemStack, item: CompletableFuture<ItemStack>): CompletableFutureItemElement = future(0, 0, fallbackItem) { item }

        // BASE
        fun reactive(cartesianCoordinate: CartesianCoordinate? = null, slot: Int? = null, fallbackItem: (Player) -> ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = ReactiveItemElement(cartesianCoordinate, slot, fallbackItem, item)
        fun reactive(x: Int, y: Int, fallbackItem: (Player) -> ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(CartesianCoordinate(x, y), null, fallbackItem, item)
        fun reactive(slot: Int, fallbackItem: (Player) -> ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(null, slot, fallbackItem, item)
        fun reactive(fallbackItem: (Player) -> ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(0, 0, fallbackItem, item)
        fun reactive(fallbackItem: (Player) -> ItemStack, item: Mono<ItemStack>): ReactiveItemElement = reactive(0, 0, fallbackItem) { item }

        fun reactive(cartesianCoordinate: CartesianCoordinate? = null, slot: Int? = null, fallbackItem: ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = ReactiveItemElement(cartesianCoordinate, slot, { fallbackItem }, item)
        fun reactive(x: Int, y: Int, fallbackItem: ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(CartesianCoordinate(x, y), null, fallbackItem, item)
        fun reactive(slot: Int, fallbackItem: ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(null, slot, fallbackItem, item)
        fun reactive(fallbackItem: ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive(0, 0, fallbackItem, item)
        fun reactive(fallbackItem: ItemStack, item: Mono<ItemStack>): ReactiveItemElement = reactive(0, 0, fallbackItem) { item }
    }
}