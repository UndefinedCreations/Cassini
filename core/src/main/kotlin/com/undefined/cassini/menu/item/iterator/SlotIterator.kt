package com.undefined.cassini.menu.item.iterator

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ItemMenu

/**
 * An [Iterable] of elements in a [ItemMenu]. Various implementations of this interface allow for easy iteration of slots in a menu.
 *
 * Elements that are `null` are air slots.
 */
interface SlotIterator {

    operator fun iterator(): Iterator<Pair<Int, ItemElement?>> // slot to a possible element

    val menu: ItemMenu<*>
    val slots: List<Int>

    /**
     * Returns the number of slots the iterator contains.
     */
    fun numberOfSlots(): Int = slots.size

    /**
     * *If needed*, calculate and update [slots].
     */
    fun calculateSlots() {}

    companion object {
        fun of(menu: ItemMenu<*>, slots: List<Int>): SlotIterator = SlotIteratorImpl(menu, slots)
        fun of(menu: ItemMenu<*>, vararg slots: Int): SlotIterator = SlotIteratorImpl(menu, slots.toList())
        fun of(menu: ItemMenu<*>, slots: IntRange): SlotIterator = SlotIteratorImpl(menu, slots.toList())
    }

}