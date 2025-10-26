package com.undefined.cassini.element.item.async

import com.undefined.cassini.element.item.ItemElement
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

/**
 * Displays [fallbackItem] until an async item has been computed, where it will then run [update].
 */
abstract class AsyncItemElement(val fallbackItem: (Player) -> ItemStack) : ItemElement() {
    companion object {
        // BASE
        fun suspend(fallbackItem: (Player) -> ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = SuspendAsyncItemElement(fallbackItem, item)
        fun suspend(fallbackItem: ItemStack, item: suspend (Player) -> ItemStack): SuspendAsyncItemElement = suspend({ fallbackItem }, item)

        // BASE
        fun future(fallbackItem: (Player) -> ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = CompletableFutureItemElement(fallbackItem, item)
        fun future(fallbackItem: (Player) -> ItemStack, item: CompletableFuture<ItemStack>): CompletableFutureItemElement = future(fallbackItem) { item }

        fun future(fallbackItem: ItemStack, item: (Player) -> CompletableFuture<ItemStack>): CompletableFutureItemElement = future({ fallbackItem }, item)
        fun future(fallbackItem: ItemStack, item: CompletableFuture<ItemStack>): CompletableFutureItemElement = future(fallbackItem) { item }

        // BASE
        fun reactive(fallbackItem: (Player) -> ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = ReactiveItemElement(fallbackItem, item)
        fun reactive(fallbackItem: (Player) -> ItemStack, item: Mono<ItemStack>): ReactiveItemElement = reactive(fallbackItem) { item }

        fun reactive(fallbackItem: ItemStack, item: (Player) -> Mono<ItemStack>): ReactiveItemElement = reactive({ fallbackItem }, item)
        fun reactive(fallbackItem: ItemStack, item: Mono<ItemStack>): ReactiveItemElement = reactive(fallbackItem) { item }
    }
}