package com.undefined.cassini.v1_21_4

import com.undefined.cassini.nms.PacketListener
import com.undefined.cassini.nms.PacketManager
import com.undefined.cassini.v1_21_4.NMS1_21_4.connection
import com.undefined.cassini.v1_21_4.NMS1_21_4.sendPacket
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class PacketListener1_21_4 private constructor(manager: PacketManager) : PacketListener(manager) {

    private val players: HashMap<UUID, UUID> = hashMapOf()

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val serverPlayer = (player as CraftPlayer).handle
        val id = UUID.randomUUID()
        players[player.uniqueId] = id

        val connection = player.connection().connection
        val channel = connection.channel
        val pipeline = channel.pipeline()
        pipeline.addBefore(
            "packet_handler",
            id.toString(),
            object : ChannelDuplexHandler() {
                override fun channelRead(channelHandlerContext: ChannelHandlerContext, packet: Any) {
                    if (packet is ServerboundContainerClosePacket) manager.onClose(player, packet.containerId)

                    if (packet is ServerboundContainerClickPacket) {
                        val clickType = MojangAdapter.getClickType(packet.clickType, packet.buttonNum, packet.changedSlots.count())
                        if (!manager.onClick(player, packet.containerId, packet.slotNum, clickType)) return run {
                            player.sendPacket(ClientboundContainerSetSlotPacket(
                                serverPlayer.containerMenu.containerId,
                                serverPlayer.containerMenu.incrementStateId(),
                                packet.slotNum,
                                serverPlayer.containerMenu.getSlot(packet.slotNum).item)
                            )
                            player.sendPacket(ClientboundSetCursorItemPacket(ItemStack.EMPTY.copy()))
                            for (entry in packet.changedSlots)
                                serverPlayer.containerMenu.setRemoteSlotNoCopy(entry.key, entry.value)
                            return
                        }
                    }

                    super.channelRead(channelHandlerContext, packet)
                }
            }
        )
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val connection = event.player.connection().connection
        val channel = connection.channel

        val id = players[event.player.uniqueId]
        channel.eventLoop().submit {
            channel.pipeline().remove(id.toString())
        }
        players.remove(id)
    }

    companion object {
        var instance: PacketListener1_21_4? = null
        fun getInstance(manager: PacketManager): PacketListener1_21_4 {
            if (instance != null) return instance!!
            instance = PacketListener1_21_4(manager)
            return instance!!
        }
    }

}