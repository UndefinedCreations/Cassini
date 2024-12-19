package com.undefined.cassini

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.item.GUIItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

abstract class Menu(var title: Component, val size: Int, val parent: Menu?) {

    private var hasBeenInitialized = false
    val items: HashMap<Int, GUIItem> = hashMapOf()
    fun firstEmptySlot(): Int {
        if (size == items.size) return -1
        return items.size + 1
    }

    @ApiStatus.Internal
    fun init() {
        if (hasBeenInitialized) return
        hasBeenInitialized = true
        initialize()
    }

    @ApiStatus.OverrideOnly
    abstract fun initialize()

    fun setItem(slot: Int, item: ItemStack) {
        items[slot] = GUIItem(item)
    }

    fun setItem(slot: Int, item: GUIItem) {
        items[slot] = item
    }

    fun addItem(item: ItemStack) {
        if (size == items.size) return
        items[firstEmptySlot()] = GUIItem(item)
    }

    fun addItem(item: GUIItem) {
        if (size == items.size) return
        items[firstEmptySlot()] = item
    }

    fun clear() {
        items.clear()
    }

    @ApiStatus.OverrideOnly
    open fun onClick(context: CassiniContext) {}

    @ApiStatus.OverrideOnly
    open fun onOpen(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun onClose(player: Player) {}

}