package com.undefined.cassini.nms

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.wrapper.AnvilMenuWrapper
import com.undefined.cassini.nms.wrapper.MenuWrapper
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface NMS {
    fun createChestMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper
    fun createAnvilMenu(player: Player, size: Int, title: Component, config: MenuConfig): AnvilMenuWrapper
    fun sendContainerClosePacket(player: Player, wrapper: MenuWrapper)
    fun sendOpenScreenPacket(player: Player, wrapper: MenuWrapper)
    fun sendOpenBookPacket(player: Player, book: ItemStack)
    fun sendContentsPacket(player: Player, wrapper: MenuWrapper)
    fun setContainerMenu(player: Player, wrapper: MenuWrapper)
    fun initMenu(player: Player, wrapper: MenuWrapper)
    fun getContainerId(player: Player): Int
}