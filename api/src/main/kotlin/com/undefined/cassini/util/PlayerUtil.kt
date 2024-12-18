package com.undefined.cassini.util

import com.undefined.cassini.MenuManager
import com.undefined.cassini.impl.ChestMenu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player

fun Player.openMenu(inventory: ChestMenu): ChestMenu {
    MenuManager.openInventory(this, inventory)
    return inventory
}

fun Player.modifyInventoryTitle(title: Component) {
    if (!openInventory.type.isCreatable) return
    this.openInventory.title = LegacyComponentSerializer.legacySection().serialize(title)
}