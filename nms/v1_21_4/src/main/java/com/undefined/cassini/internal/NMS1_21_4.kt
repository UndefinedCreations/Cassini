package com.undefined.cassini.internal

import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.world.inventory.MenuType
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
object NMS1_21_4 : NMS {

    override fun sendOpenContainerScreenPacket(player: Player) {
        val serverPlayer = (player as CraftPlayer).handle
        serverPlayer.connection.sendPacket(ClientboundOpenScreenPacket(serverPlayer.nextContainerCounter(), MenuType.GENERIC_9x6, Component.literal("test!")))
    }

}