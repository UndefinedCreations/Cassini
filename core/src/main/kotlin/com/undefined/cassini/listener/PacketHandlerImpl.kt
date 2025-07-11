package com.undefined.cassini.listener

import com.undefined.cassini.internal.listener.PacketHandler
import com.undefined.cassini.internal.info.PacketClickInformation
import com.undefined.cassini.internal.info.PacketCloseInformation
import com.undefined.cassini.menu.item.ItemMenu
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.internal.listener.DialogHandler
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

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

    override fun onCustomClickAction(player: Player, key: NamespacedKey, payload: String) {
        dialogHandler.onCustomClickAction(player, key, payload)
    }

}