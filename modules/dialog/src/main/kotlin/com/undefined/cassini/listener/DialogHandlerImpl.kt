package com.undefined.cassini.listener

import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.internal.listener.DialogHandler
import com.undefined.cassini.menu.dialog.DialogMenu
import org.bukkit.entity.Player
import java.util.UUID

object DialogHandlerImpl : DialogHandler {

    override fun onDialogClick(player: Player, buttonUUID: UUID) {
        val menu = NMSManager.openMenus[player.uniqueId] as? DialogMenu ?: return
        for (buttonAction in menu.buttons.filter { it.uuid == buttonUUID }.flatMap { it.actions }) buttonAction(player)
    }

}