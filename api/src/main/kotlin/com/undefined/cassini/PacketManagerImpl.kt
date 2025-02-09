package com.undefined.cassini

import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.nms.PacketManager
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PacketManagerImpl : PacketManager(Cassini.plugin) {
    override fun onClick(player: Player, id: Int, type: ClickType) {
        val menu = MenuManager.menus[id] ?: return
        val wrapper = MenuManager.wrappers[id]!!
        when (menu) {
            is AnvilMenu -> menu.onClick(ClickData(player, menu, id, type, wrapper.config))
            is ChestMenu -> menu.onClick(ClickData(player, menu, id, type, wrapper.config))
        }
//        menu.items[packet.slotNum]?.let { item ->
//            val context = CassiniContextImpl(player, config, clickType, item)
//            menu.onClick(context)
//            for (action in item.actions) Bukkit.getScheduler().runTask(manager.plugin, Runnable { context.action() })
//            if (context.isCancelled) return
//        }
    }

    override fun onClose(player: Player, id: Int) {
        MenuManager.menus[id]?.onClose(player)
        MenuManager.menus.remove(id)
    }

    override fun createResult(player: Player, id: Int) {
        val menu = MenuManager.menus[id] as? AnvilMenu ?: return
        menu.createResult(player)
    }
}