package com.undefined.cassini.internal

import com.undefined.cassini.data.MenuType
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
object NMS1_21_4 : NMS {

    override fun sendOpenScreenPacket(player: Player, type: MenuType, title: Component) {
        val serverPlayer = (player as CraftPlayer).handle
        serverPlayer.connection.sendPacket(ClientboundOpenScreenPacket(
            serverPlayer.nextContainerCounter(),
            MojangAdapter.getMojangMenuType(type),
            MojangAdapter.getMojangComponent(title)
        ))
    }

}