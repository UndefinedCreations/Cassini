package com.undefined.cassini.menu.item.iterator

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ChestMenu

/**
 * Represents a simple implementation [SlotIterator], storing a list of slots.
 */
class SlotIteratorImpl(override val menu: ChestMenu, slots: List<Int>) : SlotIterator {

    val _slots: List<Int> = slots
    override val slots: ListIterator<Int>
        get() = _slots.listIterator()

    init {
        for (slot in slots) {
            if (slot >= menu.size) error("Slots are over menu bounds in ${this::class.qualifiedName}")
        }
    }

    override fun numberOfSlots(): Int = _slots.size
    override fun iterator(): ListIterator<ItemElement?> = slots.asSequence().map { i -> menu.elements[i] }.toList().listIterator()

}