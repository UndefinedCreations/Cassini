package com.undefined.cassini.util

import com.undefined.cassini.Cassini
import com.undefined.cassini.Menu
import com.undefined.cassini.MenuManager
import com.undefined.cassini.impl.ChestMenu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player

fun Player.openMenu(inventory: ChestMenu, modifySlots: Boolean = Cassini.MODIFY_SLOTS): ChestMenu {
    MenuManager.openInventory(this, inventory, modifySlots)
    return inventory
}

fun Player.currentMenu(): Menu? =
    MenuManager.openMenus[this.uniqueId]