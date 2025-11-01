package com.undefined.cassini.util

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.data.ServerLink
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.menu.Menu
import org.bukkit.entity.Player

fun Player.openMenu(menu: Menu<*, *>, initialize: Boolean = true) = menu.open(this)
fun Player.closeMenu() {
    val openMenu = NMSManager.openMenus[uniqueId] ?: return
    when {
        openMenu.type.itemMenu -> closeInventory()
        openMenu.type == MenuType.DIALOG -> closeDialog()
    }
}

/**
 * Sends a list of server links to the player without modifying the actual server's server links.
 */
fun Player.setServerLinks(serverLinks: Collection<ServerLink>) = ServerLinksManager.setServerLinks(this, serverLinks)