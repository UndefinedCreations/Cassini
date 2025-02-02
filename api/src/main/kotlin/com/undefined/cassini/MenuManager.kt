package com.undefined.cassini

import com.undefined.cassini.data.event.MenuCloseEvent
import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.handlers.MenuHandler
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.NMSVersion
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.jetbrains.annotations.ApiStatus
import java.util.*

object MenuManager : Listener {

    private val hasBeenInitialized: MutableList<Menu> = mutableListOf()
    @ApiStatus.Internal val openMenus: HashMap<UUID, Menu> = hashMapOf()
    private val inventorHandlers: Map<String, MenuHandler> = mapOf(
        "1.21.3" to com.undefined.cassini.v1_21_3.MenuHandler
    )

    @EventHandler
    fun onMenuClose(event: MenuCloseEvent) {
        onClose(event)
    }

    fun update(player: Player, menu: Menu, modifySlots: Boolean = true) {
        if (menu !in hasBeenInitialized) hasBeenInitialized.add(menu)
        menu.items.clear()
        menu.preinitialize(player)
        menu.initialize(player)
        menu.afterinitialize(player)
        openMenu(player, menu, modifySlots)
    }

    fun openMenu(player: Player, menu: Menu, modifySlots: Boolean = Cassini.MODIFY_SLOTS) {
        if (menu !in hasBeenInitialized) {
            hasBeenInitialized.add(menu)
            menu.preinitialize(player)
            menu.initialize(player)
            menu.afterinitialize(player)
        }
        val handler = inventorHandlers[NMSVersion.version] ?: throw UnsupportedVersionException()
        if (player.uniqueId in openMenus && modifySlots) {
            handler.setContents(player, menu)
        } else handler.openMenu(player, menu, modifySlots)
        onOpen(player, menu)
    }

    private fun onOpen(player: Player, menu: Menu) {
        openMenus[player.uniqueId] = menu
        menu.onOpen(player)
    }

    private fun onClose(event: MenuCloseEvent) {
        openMenus.remove(event.player.uniqueId)
        event.menu.onClose(event.player)
    }

}