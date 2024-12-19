package com.undefined.cassini.data

import com.undefined.cassini.Menu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

abstract class CassiniContext(val player: Player, val menu: Menu, val type: ClickType) {
    abstract var isCancelled: Boolean
    abstract fun back()
    abstract fun close()
}