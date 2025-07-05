package com.undefined.cassini.internal

import com.undefined.cassini.data.MenuType
import net.kyori.adventure.text.Component
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerCommonPacketListenerImpl
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.inventory.ItemStack as BukkitItemStack
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
object NMS1_21_4 : NMS {

    override fun sendOpenScreenPacket(player: Player, type: MenuType, title: Component) {
        player.serverPlayer.connection.sendPacket(ClientboundOpenScreenPacket(
            CommonMenuManager.CONTAINER_ID,
            MojangAdapter.getMojangMenuType(type),
            MojangAdapter.getMojangComponent(title)
        ))
    }

    override fun sendContentsPacket(player: Player, contents: List<BukkitItemStack>) {
        player.serverPlayer.connection.sendPacket(ClientboundContainerSetContentPacket(
            CommonMenuManager.CONTAINER_ID,
            CommonMenuManager.SYNC_ID,
            MojangAdapter.getItems(contents),
            CommonMenuManager.carriedItems[player.uniqueId]?.let { CraftItemStack.asNMSCopy(it) } ?: ItemStack.EMPTY
        ))
    }

    override fun getContainerId(player: Player): Int = player.serverPlayer.nextContainerCounter()

    override fun initializePacketListener(plugin: JavaPlugin, listener: PacketListener) = NMSPacketListener1_21_4.initialize(plugin, listener)

    val Player.serverPlayer: ServerPlayer
        get() = (player as CraftPlayer).handle
    val Player.connection: Connection
        get() = ServerCommonPacketListenerImpl::class.java.getDeclaredField("e").apply { isAccessible = true }.get(serverPlayer.connection) as Connection // VERCHECK - connection

}