package com.undefined.cassini.handlers

import com.undefined.cassini.Menu
import org.bukkit.entity.Player

abstract class MenuHandler {
    abstract fun openMenu(player: Player, menu: Menu)
    abstract fun registerListeners()
}