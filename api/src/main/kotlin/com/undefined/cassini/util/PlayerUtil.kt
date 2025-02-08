package com.undefined.cassini.util

import com.undefined.cassini.Cassini
import com.undefined.cassini.Menu
import com.undefined.cassini.MenuManager
import com.undefined.cassini.exception.InvalidMenuTypeException
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.ChestMenu
import org.bukkit.entity.Player

fun <T : Menu<*>> Player.openMenu(menu: T, modifySlots: Boolean = Cassini.modifySlots): T {
    when (menu) {
        is ChestMenu -> MenuManager.openChestMenu(this, menu, modifySlots)
        is AnvilMenu -> MenuManager.openAnvilMenu(this, menu, modifySlots)
        else -> throw InvalidMenuTypeException()
    }
    return menu
}

fun <T : Menu<*>> Player.update(inventory: T): T {
    MenuManager.update(this, inventory)
    return inventory
}
