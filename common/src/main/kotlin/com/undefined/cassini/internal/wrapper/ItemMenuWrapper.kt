package com.undefined.cassini.internal.wrapper

import com.undefined.cassini.data.MenuType
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

interface ItemMenuWrapper : MenuWrapper {
    val id: Int
    val type: MenuType
    var title: Component
    fun setItem(slot: Int, item: ItemStack)
    fun clearItems()
}