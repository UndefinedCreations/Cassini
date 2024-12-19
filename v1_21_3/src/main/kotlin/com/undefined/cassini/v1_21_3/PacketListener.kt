package com.undefined.cassini.v1_21_3

import com.undefined.cassini.handlers.PacketListener
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object PacketListener : PacketListener {

    override lateinit var plugin: JavaPlugin
    val players: HashMap<UUID, UUID> = hashMapOf()

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
                    if (packet is ServerboundContainerClosePacket)
                        MenuHandler.menus.getOrDefault(packet.containerId, null)?.onClose(player)

                    if (packet is ServerboundContainerClickPacket) {
                        val menu = MenuHandler.menus.getOrDefault(packet.containerId, null) ?: return super.channelRead(channelHandlerContext, packet)
                        val clickType = MojangAdapter.getClickType(packet.clickType, packet.buttonNum, packet.changedSlots.count())
                        menu.items[packet.slotNum]?.let { item ->
                            val context = CassiniContextImpl(player, menu, clickType, item)
                            menu.onClick(context)
                            for (action in item.actions) sync { context.action() }
                            if (context.isCancelled) return
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

}