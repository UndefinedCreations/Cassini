package com.undefined.cassini.listener

import com.undefined.cassini.data.item.ClickData
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.internal.info.PacketClickInformation
import com.undefined.cassini.internal.info.PacketCloseInformation
import com.undefined.cassini.internal.listener.DialogHandler
import com.undefined.cassini.internal.listener.PacketHandler
import com.undefined.cassini.menu.item.ItemMenu
import io.papermc.paper.connection.PlayerGameConnection
import io.papermc.paper.event.player.PlayerCustomClickEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

object PacketHandlerImpl : PacketHandler, Listener {

    lateinit var dialogHandler: DialogHandler

    init {
        try {
            dialogHandler = Class.forName("com.undefined.cassini.listener.DialogHandlerImpl").getDeclaredField("INSTANCE").get(null) as DialogHandler // VERCHECK
        } catch (_: ClassNotFoundException) {}
    }

    override fun onClick(clickInformation: PacketClickInformation) {
        val slot = clickInformation.slot.toInt()
        val menu = NMSManager.openMenus[clickInformation.player.uniqueId] as? ItemMenu<*> ?: return

        if (slot > menu.size) return // player inventory

        val clickData = menu.createClickData(clickInformation.player, slot)
        menu.callClickActions(clickData)

        if (clickData.isCancelled) {
            val item = menu.items.getOrNull(slot)

            NMSManager.nms.sendSetSlotPacket(clickInformation.player, slot, item ?: ItemStack.empty())
            NMSManager.nms.sendSetCursorItemPacket(clickInformation.player, clickInformation.newItemInSlot)
        }
    }

    override fun onClose(closeInformation: PacketCloseInformation) {
        val menu = NMSManager.openMenus[closeInformation.player.uniqueId] as? ItemMenu<*> ?: return
        NMSManager.openMenus.remove(closeInformation.player.uniqueId)
        for (closeAction in menu.closeActions) closeAction(closeInformation.player)
    }

    @Suppress("UnstableApiUsage")
    @EventHandler
    fun onCustomClickEvent(event: PlayerCustomClickEvent) {
        if (event.commonConnection !is PlayerGameConnection) error("This needs to be implemented")
        dialogHandler.onCustomClickAction(
            (event.commonConnection as PlayerGameConnection).player,
            event.identifier,
            event.dialogResponseView,
        )
    }

}