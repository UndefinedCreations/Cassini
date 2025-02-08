package com.undefined.cassini.v1_21_3

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.wrapper.MenuWrapper
import com.undefined.cassini.nms.NMS
import com.undefined.cassini.nms.wrapper.AnvilMenuWrapper
import com.undefined.cassini.v1_21_3.wrapper.AnvilMenuWrapper1_21_3
import com.undefined.cassini.v1_21_3.wrapper.ChestMenuWrapper1_21_3
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import org.bukkit.entity.Player

@Suppress("ClassName")
object NMS1_21_3 : NMS {

    override fun createChestMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper = ChestMenuWrapper1_21_3(player, size, title, config)
    override fun createAnvilMenu(player: Player, size: Int, title: Component, config: MenuConfig): AnvilMenuWrapper = AnvilMenuWrapper1_21_3(player, size, title, config)

    override fun sendClosePacket(player: Player, wrapper: MenuWrapper) = player.sendPacket(ClientboundContainerClosePacket(wrapper.id))

    override fun sendOpenPacket(player: Player, wrapper: MenuWrapper) {
        val type: MenuType<*> = when (wrapper) {
            is AnvilMenuWrapper -> MenuType.ANVIL
            else -> MojangAdapter.getMenuType(wrapper.size)
        }
        player.sendPacket(ClientboundOpenScreenPacket(wrapper.id, type, MojangAdapter.getComponent(wrapper.title)))
    }

    override fun sendContentsPacket(player: Player, wrapper: MenuWrapper) {
        player.sendPacket(ClientboundContainerSetContentPacket(
            wrapper.id,
            player.serverPlayer().containerMenu.stateId,
            MojangAdapter.getItems(wrapper),
            MojangAdapter.emptyItem
        ))
    }

    override fun setContainerMenu(player: Player, wrapper: MenuWrapper) {
        player.serverPlayer().containerMenu = wrapper as AbstractContainerMenu
    }

    override fun initMenu(player: Player, wrapper: MenuWrapper) = player.serverPlayer().initMenu(wrapper as AbstractContainerMenu)

}