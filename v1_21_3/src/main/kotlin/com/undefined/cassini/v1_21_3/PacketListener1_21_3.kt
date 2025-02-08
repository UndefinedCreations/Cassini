package com.undefined.cassini.v1_21_3

import com.undefined.cassini.nms.PacketListener
import com.undefined.cassini.nms.PacketManager
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class PacketListener1_21_3 private constructor(manager: PacketManager) : PacketListener(manager) {

    private val players: HashMap<UUID, UUID> = hashMapOf()

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
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
                    if (packet is ServerboundContainerClosePacket) {
                        println("closing...")
                        manager.onClose(player, packet.containerId)
                    }

                    if (packet is ServerboundContainerClickPacket) {
                        println("click packet")
                        val clickType = MojangAdapter.getClickType(packet.clickType, packet.buttonNum, packet.changedSlots.count())
                        manager.onClick(player, packet.containerId, clickType)
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
        var instance: PacketListener1_21_3? = null
        fun getInstance(manager: PacketManager): PacketListener1_21_3 {
            if (instance != null) return instance!!
            instance = PacketListener1_21_3(manager)
            return instance!!
        }
    }

}