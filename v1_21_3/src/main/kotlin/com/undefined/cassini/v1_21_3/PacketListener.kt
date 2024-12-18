package com.undefined.cassini.v1_21_3

import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.handlers.PacketListener
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

object PacketListener : PacketListener {

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
                override fun channelRead(context: ChannelHandlerContext, packet: Any) {
                    if (packet is ServerboundContainerClosePacket)
                        MenuHandler.menus.getOrDefault(packet.containerId, null)?.onClose(player)

                    if (packet is ServerboundContainerClickPacket) {
                        val menu = MenuHandler.menus.getOrDefault(packet.containerId, null) ?: return super.channelRead(context, packet)
                        val data = ClickData(
                            player,
                            MenuAdapter.getClickType(packet.clickType, packet.buttonNum, packet.changedSlots.count())
                        )
                        menu.onClick(data)
                        menu.items[packet.slotNum]?.let { item ->
                            for (action in item.customActions) action(data)
                            if (ActionAdapter.handleClickActions(player, item, menu)) return
                        }
                    }

                    super.channelRead(context, packet)
                }

                override fun write(context: ChannelHandlerContext, packet: Any, promise: ChannelPromise) {
                    if (packet is ClientboundContainerClosePacket) {
                        println("clientbound container close packet")
                        return
                    }
                    super.write(context, packet, promise)
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