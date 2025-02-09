package com.undefined.cassini.data.click

import com.undefined.cassini.Menu
import com.undefined.cassini.MenuManager
import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.util.openMenu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ClickData<T : Menu<T>>(val player: Player, val menu: T, val slot: Int, val id: Int, val type: ClickType, val config: MenuConfig) {

    var isCancelled: Boolean = false

    fun back() {
        menu.parent?.let { previousMenu ->
            if (config.modifySlots) {
                MenuManager.packetManager.onClose(player, id)
                MenuManager.nms.sendContentsPacket(player, MenuManager.wrappers[id]!!)
            } else {
                close()
                player.openMenu(previousMenu, false)
            }
        } ?: close()
    }

    fun close() {
        val wrapper = MenuManager.wrappers[id]!!
        MenuManager.nms.sendClosePacket(player, wrapper)
        MenuManager.packetManager.onClose(player, id)
    }

}