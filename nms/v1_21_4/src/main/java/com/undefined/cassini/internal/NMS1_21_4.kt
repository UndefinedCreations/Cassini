package com.undefined.cassini.internal

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.menu.item.ItemMenu
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack as BukkitItemStack
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
object NMS1_21_4 : NMS {

    override fun sendOpenScreenPacket(player: Player, type: MenuType, title: Component) {
        player.serverPlayer().connection.sendPacket(ClientboundOpenScreenPacket(
            ItemMenu.CONTAINER_ID,
            MojangAdapter.getMojangMenuType(type),
            MojangAdapter.getMojangComponent(title)
        ))
    }

    override fun sendContentsPacket(player: Player, contents: List<BukkitItemStack>) {
        player.serverPlayer().connection.sendPacket(ClientboundContainerSetContentPacket(
            ItemMenu.CONTAINER_ID,
            ItemMenu.SYNC_ID,
            MojangAdapter.getItems(contents),
            ItemMenu.carriedItems[player.uniqueId]?.let { CraftItemStack.asNMSCopy(it) } ?: ItemStack.EMPTY
        ))
    }

    override fun getContainerId(player: Player): Int = player.serverPlayer().nextContainerCounter()

    fun Player.serverPlayer(): ServerPlayer = (player as CraftPlayer).handle

}