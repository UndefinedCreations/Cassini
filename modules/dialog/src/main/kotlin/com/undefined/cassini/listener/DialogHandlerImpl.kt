package com.undefined.cassini.listener

import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.internal.listener.DialogHandler
import com.undefined.cassini.menu.dialog.DialogMenu
import io.papermc.paper.dialog.DialogResponseView
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import java.util.*

object DialogHandlerImpl : DialogHandler {

    override fun onCustomClickAction(player: Player, key: Key, payload: DialogResponseView) {
        if (key.namespace() != "cassini") return
        val menu = NMSManager.openMenus[player.uniqueId] as? DialogMenu ?: return
        val buttonUUID = UUID.fromString(key.value())
        for (buttonAction in menu.totalButtons.filter { it.uuid == buttonUUID }.flatMap { it.actions }) buttonAction(player, payload)
    }

}