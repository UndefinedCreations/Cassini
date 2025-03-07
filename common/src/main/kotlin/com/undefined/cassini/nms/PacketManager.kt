package com.undefined.cassini.nms

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

interface PacketManager {
    fun onClick(player: Player, id: Int, slot: Int, type: ClickType): Boolean
    fun onClose(player: Player, id: Int)
    fun createResult(player: Player, id: Int)
}