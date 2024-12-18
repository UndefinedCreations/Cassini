package com.undefined.cassini

import com.undefined.cassini.handlers.MenuHandler
import com.undefined.cassini.impl.ChestMenu
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object MenuManager {

    private val version by lazy { Bukkit.getBukkitVersion().split("-")[0] }
    private val inventorHandlers: Map<String, MenuHandler> = mapOf(
        "1.21.3" to com.undefined.cassini.v1_21_3.MenuHandler
    )

    fun openInventory(player: Player, menu: ChestMenu) {
        menu.init()
        inventorHandlers[version]?.openInventory(player, menu)
        onOpenMenu(player, menu)
    }

    private fun onOpenMenu(player: Player, menu: Menu) {
        Cassini.openMenus[player.uniqueId] = menu
        menu.onOpen(player)
    }

}