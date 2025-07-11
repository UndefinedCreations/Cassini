package com.undefined.cassini.listener

import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.internal.listener.DialogHandler
import com.undefined.cassini.menu.dialog.DialogMenu
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import java.util.UUID

object DialogHandlerImpl : DialogHandler {

    override fun onCustomClickAction(player: Player, key: NamespacedKey, payload: String) {
        if (key.key != "cassini") return
        val menu = NMSManager.openMenus[player.uniqueId] as? DialogMenu ?: return
        val buttonUUID = UUID.fromString(key.namespace)
        for (buttonAction in menu.totalButtons.filter { it.uuid == buttonUUID }.flatMap { it.actions }) buttonAction(player)
    }

}