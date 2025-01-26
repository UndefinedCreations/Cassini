package com.undefined.cassini

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.item.GUIItem
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

abstract class Menu(var title: Component, val size: Int, val optimization: MenuOptimization, val parent: Menu?) {

    private var hasBeenInitialized = false
    open val items: HashMap<Int, GUIItem> = hashMapOf()
    fun firstEmptySlot(): Int {
        if (size == items.size) return -1
        return items.size + 1
    }

    @ApiStatus.OverrideOnly
    open fun preinitialize(player: Player) {}

    @ApiStatus.OverrideOnly
    abstract fun initialize(player: Player)

    @ApiStatus.OverrideOnly
    open fun afterinitialize(player: Player) {}

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

    fun setItems(item: GUIItem, slots: List<Int>) {
        for (slot in slots) setItem(slot, item)
    }

    fun setItems(item: GUIItem, vararg slots: Int) = setItems(item, slots.toList())

    fun setItems(item: ItemStack, vararg slots: Int) = setItems(item, slots.toList())

    fun setItems(item: ItemStack, slots: List<Int>) = setItems(GUIItem(item), slots)

    fun setItems(item: GUIItem, slots: IntRange) = setItems(item, slots.toList())

    fun setItems(item: ItemStack, slots: IntRange) = setItems(item, slots.toList())

    fun isSlotEmpty(slot: Int) = !items.containsKey(slot)

    fun clear() = items.clear()

    @ApiStatus.OverrideOnly
    open fun onClick(context: CassiniContext) {}

    @ApiStatus.OverrideOnly
    open fun onOpen(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun onClose(player: Player) {}

}