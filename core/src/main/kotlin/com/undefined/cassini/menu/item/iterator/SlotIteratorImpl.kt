package com.undefined.cassini.menu.item.iterator

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ItemMenu

/**
 * Represents a simple implementation [SlotIterator], storing a list of slots.
 */
open class SlotIteratorImpl(override val menu: ItemMenu<*>, override val slots: List<Int>) : SlotIterator {
    init {
        for (slot in slots) {
            if (slot >= menu.size) error("Slots are over menu bounds in ${this::class.qualifiedName}")
        }
    }

    override fun iterator(): Iterator<Pair<Int, ItemElement?>> = slots.asSequence().map { i -> i to menu.elements[i] }.iterator()
}