package com.undefined.cassini.handlers

import com.undefined.cassini.Menu
import org.bukkit.entity.Player

abstract class MenuHandler {
    abstract fun openMenu(player: Player, menu: Menu, modifySlots: Boolean)
    abstract fun setContents(player: Player, menu: Menu)
    abstract fun setAnvilText(player: Player, text: String)
    abstract fun registerListeners()
}