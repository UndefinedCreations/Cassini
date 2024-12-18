package com.undefined.cassini.handlers

import com.undefined.cassini.impl.ChestMenu
import org.bukkit.entity.Player

abstract class MenuHandler {
    abstract fun openInventory(player: Player, menu: ChestMenu)
}