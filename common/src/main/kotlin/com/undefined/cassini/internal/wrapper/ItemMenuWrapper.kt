package com.undefined.cassini.internal.wrapper

import com.undefined.cassini.data.MenuType
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface ItemMenuWrapper : MenuWrapper {
    val id: Int
    val type: MenuType
    var title: Component
    fun setItem(slot: Int, item: ItemStack?)
    fun open(player: Player)

    fun clearItems() {
        for (i in 0 until type.size!!) setItem(i, null)
    }
}