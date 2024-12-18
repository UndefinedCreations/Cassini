package com.undefined.cassini

import com.undefined.cassini.data.click.ClickData
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

abstract class Menu(var title: Component, val parent: Menu?) {

    var player: Player? = null
    val items: MutableList<ItemStack> = mutableListOf()

    abstract fun updateTitle()
    fun updateTitle(title: Component) {
        this.title = title
        updateTitle()
    }

    fun setItem(slot: Int, item: ItemStack) {
        items[slot] = item
    }

    fun addItem(item: ItemStack) {
        items.add(item)
    }

    fun firstEmptySlot(): Int = items.lastIndex + 1

//    fun returnMenu() {
//        player?.let {
//            parent?.open(it) ?: it.closeInventory()
//        }
//    }

    @ApiStatus.OverrideOnly
    fun onClick(data: ClickData) {}

    @ApiStatus.OverrideOnly
    fun onOpen(data: Player) {}

    @ApiStatus.OverrideOnly
    fun onClose(data: Player) {}

}