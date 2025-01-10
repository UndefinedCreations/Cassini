package com.undefined.cassini.v1_21_3

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.item.GUIItem
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class CassiniContextImpl(player: Player, val config: MenuConfig, type: ClickType, val item: GUIItem) : CassiniContext(player, config.menu, type) {

    override var isCancelled: Boolean = false

    override fun back() {
        menu.parent?.let { previousMenu ->
            if (config.modifySlots) {
                MenuHandler.onClose(player, menu)
                MenuHandler.setContents(player, menu)
            } else {
                close()
                MenuHandler.openMenu(player, previousMenu, true)
            }
        } ?: close()
    }

    override fun close() {
        val id = MenuHandler.menus.filterValues { it.menu == menu }.keys.firstOrNull() ?: return
        player.connection().sendPacket(ClientboundContainerClosePacket(id))
        MenuHandler.onClose(player, menu)
    }

}