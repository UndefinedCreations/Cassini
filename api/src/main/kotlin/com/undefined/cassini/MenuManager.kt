package com.undefined.cassini

import com.undefined.cassini.handlers.MenuHandler
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.NMSVersion
import org.bukkit.entity.Player

object MenuManager {

    private val inventorHandlers: Map<String, MenuHandler> = mapOf(
        "1.21.3" to com.undefined.cassini.v1_21_3.MenuHandler
    )

    fun openInventory(player: Player, menu: ChestMenu) {
        menu.init()
        val handler = inventorHandlers[NMSVersion.version]
        handler?.openMenu(player, menu)
        onOpenMenu(player, menu)
    }

    private fun onOpenMenu(player: Player, menu: Menu) {
        Cassini.openMenus[player.uniqueId] = menu
        menu.onOpen(player)
    }

}