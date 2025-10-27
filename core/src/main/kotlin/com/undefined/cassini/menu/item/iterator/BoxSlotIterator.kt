package com.undefined.cassini.menu.item.iterator

import com.undefined.cassini.menu.item.ItemMenu

/**
 * Represents an implementation [SlotIterator], containing a list of slots calculated from a width and height.
 */
class BoxSlotIterator(
    menu: ItemMenu<*>,
    val startSlot: Int,
    val width: Int,
    val height: Int,
) : SlotIteratorImpl(menu, mutableListOf<Int>().also { slots ->
    for (row in 0 until height) {
        for (col in 0 until width) {
            val slot = startSlot + row * menu.maxWidth + col
            slots.add(slot)
        }
    }
})