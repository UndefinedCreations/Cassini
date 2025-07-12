package com.undefined.cassini.util

import com.undefined.cassini.CassiniConfig
import com.undefined.cassini.data.ServerLink
import com.undefined.cassini.internal.NMSManager
import org.bukkit.entity.Player

/**
 * Mostly a util class for sending and setting server links.
 */
object ServerLinksManager {

    /**
     * Sends a list of server links to the player without modifying the actual server's server links.
     */
    fun setServerLinks(player: Player, serverLinks: Collection<ServerLink>) = NMSManager.nms.sendServerLinks(player, serverLinks)

    /**
     * Sets the server links on the server, and sends each online player an update.
     */
    fun setServerLinks(serverLinks: Collection<ServerLink>) {
        NMSManager.nms.setServerLinks(CassiniConfig.plugin.server, serverLinks)
        for (player in CassiniConfig.plugin.server.onlinePlayers) setServerLinks(player, serverLinks)
    }

}