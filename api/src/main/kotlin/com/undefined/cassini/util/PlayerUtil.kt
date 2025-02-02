package com.undefined.cassini.util

import com.undefined.cassini.Cassini
import com.undefined.cassini.Menu
import com.undefined.cassini.MenuManager
import org.bukkit.entity.Player

fun <T : Menu> Player.openMenu(menu: T, modifySlots: Boolean = Cassini.MODIFY_SLOTS): T {
    MenuManager.openMenu(this, menu, modifySlots)
    return menu
}

fun <T : Menu> Player.update(inventory: T, modifySlots: Boolean = Cassini.MODIFY_SLOTS): T {
    MenuManager.update(this, inventory, modifySlots)
    return inventory
}

fun Player.currentMenu(): Menu? =
    MenuManager.openMenus[this.uniqueId]