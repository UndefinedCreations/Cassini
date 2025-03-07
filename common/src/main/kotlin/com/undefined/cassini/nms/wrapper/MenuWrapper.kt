package com.undefined.cassini.nms.wrapper

import com.undefined.cassini.data.MenuConfig
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

interface MenuWrapper {
    val id: Int
    val size: Int
    var title: Component
    val config: MenuConfig
    fun setItem(slot: Int, item: ItemStack)
}