package com.undefined.cassini.v1_21_3

import com.undefined.cassini.handlers.MenuHandler
import com.undefined.cassini.impl.ChestMenu
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import org.bukkit.entity.Player

object MenuHandler : MenuHandler() {

    override fun openInventory(player: Player, menu: ChestMenu) {
        val type = MenuAdapter.getMenuType(menu.size)
        val syncId = -1
        val title = ComponentAdapter.getComponent(menu.title)
        val packet = ClientboundOpenScreenPacket(syncId, type, title)
        player.connection().sendPacket(packet)
    }

}