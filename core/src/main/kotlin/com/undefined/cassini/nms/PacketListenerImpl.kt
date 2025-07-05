package com.undefined.cassini.nms

import com.undefined.cassini.internal.PacketListener
import com.undefined.cassini.internal.info.PacketClickInformation
import com.undefined.cassini.menu.item.ItemMenu

object PacketListenerImpl : PacketListener {

    override fun onClick(clickInformation: PacketClickInformation) {
        val menu = NMSManager.openMenus[clickInformation.player.uniqueId] as? ItemMenu<*> ?: return
        val clickData = menu.createClickData(clickInformation.player, clickInformation.slot)
        menu.callClickActions(clickData)
    }

}