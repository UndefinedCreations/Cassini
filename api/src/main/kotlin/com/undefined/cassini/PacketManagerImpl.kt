package com.undefined.cassini

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.nms.PacketManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PacketManagerImpl : PacketManager(Cassini.plugin) {
    override fun onClick(player: Player, id: Int, slot: Int, type: ClickType): Boolean {
        val menu = MenuManager.menus[id] ?: return true
        val wrapper = MenuManager.wrappers[id]!!
        return when (menu) {
            is AnvilMenu -> handleClick(player, menu, slot, id, type, wrapper.config)
            is ChestMenu -> handleClick(player, menu, slot, id, type, wrapper.config)
            else -> true
        }
    }

    override fun onClose(player: Player, id: Int) {
        MenuManager.menus[id]?.onClose(player)
        MenuManager.menus.remove(id)
    }

    override fun createResult(player: Player, id: Int) {
        val menu = MenuManager.menus[id] as? AnvilMenu ?: return
        menu.createResult(player)
    }

    private inline fun <reified T : Menu<T>> handleClick(player: Player, menu: T, slot: Int, id: Int, type: ClickType, config: MenuConfig): Boolean {
        val data = ClickData(player, menu, id, slot, type, config)
        menu.onClick(data)
        menu.items[slot]?.let { item ->
            for (action in item.actions) Bukkit.getScheduler().runTask(plugin, Runnable { data.action() })
            if (data.isCancelled) return false
        }
        return true
    }

}