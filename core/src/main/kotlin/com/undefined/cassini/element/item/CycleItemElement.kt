package com.undefined.cassini.element.item

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents a list of items to be displayed after one another on each [next] call in a [com.undefined.cassini.menu.item.ItemMenu]. It circles back when it has reached the end of the array.
 */
class CycleItemElement(val items: Array<ItemStack>) : ItemElement() {

    private var currentIndex: Int = 0

    override fun getItem(player: Player): ItemStack {
        if (items.size < 2) throw IllegalStateException("There must be at least one item in the list.")
        return items[currentIndex]
    }

    /**
     * Cycles to the next item.
     */
    fun next() {
        if (currentIndex >= items.size - 1) currentIndex = 0
        else currentIndex++
        update()
    }

    /**
     * Cycles to the previous item.
     */
    fun previous() {
        if (currentIndex <= 0) return
        currentIndex--
        update()
    }

}