package com.undefined.cassini.internal

import org.bukkit.entity.Player

interface NMS {
//    fun createChestMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper
//    fun createAnvilMenu(player: Player, size: Int, title: Component, config: MenuConfig): AnvilMenuWrapper
//    fun createSmithingMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper
//    fun sendContainerClosePacket(player: Player, wrapper: MenuWrapper)
    fun sendOpenContainerScreenPacket(player: Player)
//    fun sendOpenBookPacket(player: Player, book: ItemStack)
//    fun sendContentsPacket(player: Player, wrapper: MenuWrapper)
//    fun setContainerMenu(player: Player, wrapper: MenuWrapper)
//    fun resetContainerMenu(player: Player)
//    fun initMenu(player: Player, wrapper: MenuWrapper)
//    fun getContainerId(player: Player): Int
}