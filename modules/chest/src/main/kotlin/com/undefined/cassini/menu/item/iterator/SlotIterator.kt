package com.undefined.cassini.menu.item.iterator

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ChestMenu

/**
 * An [Iterable] of elements in a [ChestMenu]. Various implementations of this interface allow for easy iteration of slots in a menu.
 *
 * Elements that are `null` are air slots.
 */
interface SlotIterator {

    operator fun iterator(): ListIterator<ItemElement?>

    val menu: ChestMenu
    val slots: ListIterator<Int>

    /**
     * Returns the number of slots the iterator contains.
     */
    fun numberOfSlots(): Int

    /**
     * *If needed*, calculate and update [slots].
     */
    fun calculateSlots() {}

    companion object {
        fun of(menu: ChestMenu, slots: List<Int>): SlotIterator = SlotIteratorImpl(menu, slots)
        fun of(menu: ChestMenu, vararg slots: Int): SlotIterator = SlotIteratorImpl(menu, slots.toList())
        fun of(menu: ChestMenu, slots: IntRange): SlotIterator = SlotIteratorImpl(menu, slots.toList())
    }

}