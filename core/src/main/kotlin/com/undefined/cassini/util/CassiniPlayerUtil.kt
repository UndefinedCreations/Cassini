package com.undefined.cassini.util

import com.undefined.cassini.data.ServerLink
import com.undefined.cassini.menu.CassiniMenu
import org.bukkit.entity.Player

fun Player.openMenu(menu: CassiniMenu<*, *>) = menu.open(this)

/**
 * Sends a list of server links to the player without modifying the actual server's server links.
 */
fun Player.setServerLinks(serverLinks: Collection<ServerLink>) = ServerLinksManager.setServerLinks(this, serverLinks)