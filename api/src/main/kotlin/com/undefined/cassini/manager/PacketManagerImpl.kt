package com.undefined.cassini.manager

import com.undefined.cassini.Cassini
import com.undefined.cassini.ContainerMenu
import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.event.MenuClickEvent
import com.undefined.cassini.event.MenuOpenEvent
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.nms.PacketManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class PacketManagerImpl : PacketManager {
    override fun onClick(player: Player, id: Int, slot: Int, type: ClickType): Boolean {
        val menu = MenuManager.menus[id] as? ContainerMenu<*> ?: return true

        if (MenuClickEvent(player, menu, slot, type).apply {
            Bukkit.getScheduler().runTask(Cassini.plugin, Runnable { callEvent() })
        }.isCancelled) return false

        val wrapper = MenuManager.wrappers[id]!!
        return when (menu) {
            is ChestMenu -> handleClick(player, menu, slot, id, type, wrapper.config)
            is AnvilMenu -> handleClick(player, menu, slot, id, type, wrapper.config)
            else -> true
        }
    }

    override fun onClose(player: Player, id: Int) {
        val menu = MenuManager.menus[id] ?: throw IllegalArgumentException("No menu with id!")
        if (MenuOpenEvent(player, menu).apply { callEvent() }.isCancelled) return

        MenuManager.menus[id]?.onClose(player)
        MenuManager.menus.remove(id)
        MenuManager.wrappers.remove(id)
    }

    override fun createResult(player: Player, id: Int) {
        val menu = MenuManager.menus[id] as? AnvilMenu ?: return
        menu.createResult(player)
    }

    private inline fun <reified T : ContainerMenu<T>> handleClick(player: Player, menu: T, slot: Int, id: Int, type: ClickType, config: MenuConfig): Boolean {
        val data = ClickData(player, menu, slot, id, type, config)
        menu.onClick(data)
        menu.items[slot]?.let { item ->
            for (action in item.actions) data.action()
        }
        return !data.isCancelled
    }

}