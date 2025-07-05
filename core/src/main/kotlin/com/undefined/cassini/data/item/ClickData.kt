package com.undefined.cassini.data.item

import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class ClickData<T : ItemMenu<*>>(
    val player: Player,
    val menu: T,
    val slot: Int,
    val id: Int,
//    val type: ClickType, TODO add custom click type
    val items: List<ItemStack> = player.openInventory.topInventory.contents.toList(),
) {

    var isCancelled: Boolean = false

    fun back() {
        // TODO
    }

    fun close() {
        // TODO
    }

}