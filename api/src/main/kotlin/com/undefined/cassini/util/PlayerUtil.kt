package com.undefined.cassini.util

import com.undefined.cassini.Cassini
import com.undefined.cassini.Menu
import com.undefined.cassini.exception.InvalidMenuTypeException
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.BookMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.manager.MenuManager
import org.bukkit.entity.Player

fun <T : Menu<*>> Player.openMenu(menu: T, modifySlots: Boolean = Cassini.modifySlots): T {
    when (menu) {
        is ChestMenu -> MenuManager.openChestMenu(this, menu, modifySlots)
        is AnvilMenu -> MenuManager.openAnvilMenu(this, menu, modifySlots)
        is BookMenu -> MenuManager.openBookMenu(this, menu)
        else -> throw InvalidMenuTypeException()
    }
    return menu
}

fun <T : Menu.Builder<*, M>, M : Menu<*>> Player.openBuilderMenu(menu: T, modifySlots: Boolean = Cassini.modifySlots): M = openMenu(menu.build(), modifySlots)

fun <T : Menu<*>> Player.update(inventory: T): T {
    MenuManager.update(this, inventory)
    return inventory
}
