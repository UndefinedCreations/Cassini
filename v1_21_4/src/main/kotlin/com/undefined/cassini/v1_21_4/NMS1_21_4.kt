package com.undefined.cassini.v1_21_4

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.NMS
import com.undefined.cassini.nms.wrapper.AnvilMenuWrapper
import com.undefined.cassini.nms.wrapper.MenuWrapper
import com.undefined.cassini.v1_21_4.wrapper.AnvilMenuWrapper1_21_4
import com.undefined.cassini.v1_21_4.wrapper.ChestMenuWrapper1_21_4
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.InteractionHand
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@Suppress("ClassName")
object NMS1_21_4 : NMS {

    override fun createChestMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper = ChestMenuWrapper1_21_4(player, size, title, config)
    override fun createAnvilMenu(player: Player, size: Int, title: Component, config: MenuConfig): AnvilMenuWrapper = AnvilMenuWrapper1_21_4(player, size, title, config)

    override fun sendContainerClosePacket(player: Player, wrapper: MenuWrapper) = player.sendPacket(ClientboundContainerClosePacket(wrapper.id))
    override fun sendOpenScreenPacket(player: Player, wrapper: MenuWrapper) {
        val type: MenuType<*> = when (wrapper) {
            is AnvilMenuWrapper -> MenuType.ANVIL
            else -> MojangAdapter.getMenuType(wrapper.size)
        }
        player.sendPacket(ClientboundOpenScreenPacket(wrapper.id, type, MojangAdapter.getComponent(wrapper.title)))
    }
    override fun sendOpenBookPacket(player: Player, book: ItemStack) = player.serverPlayer().openItemGui(CraftItemStack.asNMSCopy(book), InteractionHand.MAIN_HAND)
    override fun sendContentsPacket(player: Player, wrapper: MenuWrapper) {
        player.sendPacket(ClientboundContainerSetContentPacket(
            wrapper.id,
            player.serverPlayer().containerMenu.stateId,
            MojangAdapter.getItems(wrapper),
            MojangAdapter.emptyItem
        ))
    }

    override fun setContainerMenu(player: Player, wrapper: MenuWrapper) { player.serverPlayer().containerMenu = wrapper as AbstractContainerMenu }
    override fun initMenu(player: Player, wrapper: MenuWrapper) = player.serverPlayer().initMenu(wrapper as AbstractContainerMenu)

    override fun getContainerId(player: Player): Int = player.serverPlayer().containerMenu.containerId

    fun Player.craftPlayer(): CraftPlayer = player as CraftPlayer
    fun Player.serverPlayer(): ServerPlayer = craftPlayer().handle
    fun Player.connection(): ServerGamePacketListenerImpl = serverPlayer().connection
    fun Player.sendPacket(packet: Packet<*>) = connection().sendPacket(packet)

}