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
import java.util.*

object PacketListener : PacketListener {

    override lateinit var plugin: JavaPlugin
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
                        MenuHandler.menus.getOrDefault(packet.containerId, null)?.let { config ->
                            println("close")
                            return MenuHandler.onClose(player, config.menu)
                        }
                    }

                    if (packet is ServerboundContainerClickPacket) {
                        println("click packet")
                        val config = MenuHandler.menus.getOrDefault(packet.containerId, null) ?: return super.channelRead(channelHandlerContext, packet)
                        val menu = config.menu
                        val clickType = MojangAdapter.getClickType(packet.clickType, packet.buttonNum, packet.changedSlots.count())
                        menu.items[packet.slotNum]?.let { item ->
                            val context = CassiniContextImpl(player, config, clickType, item)
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