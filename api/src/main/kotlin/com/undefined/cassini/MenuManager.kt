package com.undefined.cassini

import com.undefined.cassini.handlers.MenuHandler
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.impl.PaginatedMenu
import com.undefined.cassini.util.NMSVersion
import org.bukkit.entity.Player

object MenuManager {

    private val hasBeenInitialized: MutableList<Menu> = mutableListOf()
    private val inventorHandlers: Map<String, MenuHandler> = mapOf(
        "1.21.3" to com.undefined.cassini.v1_21_3.MenuHandler
    )

    fun openInventory(player: Player, menu: ChestMenu, modifySlots: Boolean = Cassini.MODIFY_SLOTS) {
        if (menu !in hasBeenInitialized) {
            hasBeenInitialized.add(menu)
            menu.initialize()
            if (menu is PaginatedMenu<*>) initializePaginatedMenu(player, menu)
        }
        val handler = inventorHandlers[NMSVersion.version]
        handler?.openMenu(player, menu, modifySlots)
        onOpenMenu(player, menu)
    }

    private fun initializePaginatedMenu(player: Player, menu: PaginatedMenu<*>) {
        if (menu.currentPage > 1) {
            menu.setItem(menu.backButton.slot, menu.backButton.activeItem)
        } else {
            menu.setItem(menu.backButton.slot, menu.backButton.inactiveItem)
        }
    }

    private fun onOpenMenu(player: Player, menu: Menu) {
        Cassini.openMenus[player.uniqueId] = menu
        menu.onOpen(player)
    }

}