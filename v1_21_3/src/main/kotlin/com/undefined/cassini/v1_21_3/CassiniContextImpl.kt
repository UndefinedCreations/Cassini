package com.undefined.cassini.v1_21_3

import com.undefined.cassini.Menu
import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.item.GUIItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class CassiniContextImpl(player: Player, menu: Menu, type: ClickType, item: GUIItem) : CassiniContext(player, menu, type) {

    private val serverPlayer = player.serverPlayer()

    override var isCancelled: Boolean = false

    override fun back() {
        menu.parent?.let { MenuHandler.setContents(player, menu) } ?: serverPlayer.closeContainer()
    }

    override fun close() {
        serverPlayer.closeContainer()
    }

}