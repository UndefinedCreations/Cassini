package com.undefined.cassini.nms

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.wrapper.AnvilMenuWrapper
import com.undefined.cassini.nms.wrapper.MenuWrapper
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface NMS {
    fun createChestMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper
    fun createAnvilMenu(player: Player, size: Int, title: Component, config: MenuConfig): AnvilMenuWrapper
    fun sendClosePacket(player: Player, wrapper: MenuWrapper)
    fun sendOpenPacket(player: Player, wrapper: MenuWrapper)
    fun sendContentsPacket(player: Player, wrapper: MenuWrapper)
    fun setContainerMenu(player: Player, wrapper: MenuWrapper)
    fun initMenu(player: Player, wrapper: MenuWrapper)
}