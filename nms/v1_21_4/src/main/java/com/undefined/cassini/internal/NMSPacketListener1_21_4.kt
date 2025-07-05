package com.undefined.cassini.internal

import com.undefined.cassini.internal.NMS1_21_4.connection
import com.undefined.cassini.internal.info.PacketClickInformation
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket
import net.minecraft.server.network.ServerCommonPacketListenerImpl
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object NMSPacketListener1_21_4 : Listener {

    lateinit var listener: PacketListener

    private val players: HashMap<UUID, UUID> = hashMapOf()

    fun initialize(plugin: JavaPlugin, listener: PacketListener) {
        this.listener = listener
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player as? CraftPlayer ?: return
        val id = UUID.randomUUID()
        players[player.uniqueId] = id

        val connection = player.connection
        val channel = connection.channel
        val pipeline = channel.pipeline()

        pipeline.addBefore(
            "packet_handler",
            id.toString(),
            object : ChannelDuplexHandler() {
                override fun channelRead(channelHandlerContext: ChannelHandlerContext, packet: Any) {
                    if (packet is ServerboundContainerClosePacket) player.sendMessage("close packet")
                    if (packet is ServerboundContainerClickPacket) {
                        listener.onClick(PacketClickInformation(player, packet.slotNum))
                    }

                    super.channelRead(channelHandlerContext, packet)
                }
            }
        )
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val connection = event.player.connection
        val channel = connection.channel

        val id = players[event.player.uniqueId]
        channel.eventLoop().submit {
            channel.pipeline().remove(id.toString())
        }
        players.remove(id)
    }

}