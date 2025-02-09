package com.undefined.cassini.nms

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.plugin.java.JavaPlugin

abstract class PacketManager(val plugin: JavaPlugin) {
    abstract fun onClick(player: Player, id: Int, type: ClickType)
    abstract fun onClose(player: Player, id: Int)
    abstract fun createResult(player: Player, id: Int)
}