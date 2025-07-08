package com.undefined.cassini.listener

import com.undefined.cassini.internal.listener.PacketHandler
import com.undefined.cassini.internal.info.PacketClickInformation
import com.undefined.cassini.internal.info.PacketCloseInformation
import com.undefined.cassini.menu.item.ItemMenu
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.internal.listener.DialogHandler
import org.bukkit.entity.Player
import java.awt.Dialog
import java.util.UUID

object PacketHandlerImpl : PacketHandler {

    lateinit var dialogHandler: DialogHandler

    init {
        try {
            dialogHandler = Class.forName("com.undefined.cassini.listener.DialogHandlerImpl").getDeclaredField("INSTANCE").get(null) as DialogHandler // VERCHECK
        } catch (_: ClassNotFoundException) {}
    }

    override fun onClick(clickInformation: PacketClickInformation) {
        val menu = NMSManager.openMenus[clickInformation.player.uniqueId] as? ItemMenu<*> ?: return
        val clickData = menu.createClickData(clickInformation.player, clickInformation.slot)
        menu.callClickActions(clickData)
    }

    override fun onClose(closeInformation: PacketCloseInformation) {
        val menu = NMSManager.openMenus[closeInformation.player.uniqueId] as? ItemMenu<*> ?: return
        NMSManager.openMenus.remove(closeInformation.player.uniqueId)
        for (closeAction in menu.closeActions) closeAction(closeInformation.player)
    }

    override fun onDialogClick(player: Player, buttonUUID: UUID) {
        dialogHandler.onDialogClick(player, buttonUUID)
    }

}