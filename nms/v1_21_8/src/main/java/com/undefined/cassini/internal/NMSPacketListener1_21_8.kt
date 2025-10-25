package com.undefined.cassini.internal

import com.undefined.cassini.internal.info.PacketClickInformation
import com.undefined.cassini.internal.info.PacketCloseInformation
import com.undefined.cassini.internal.listener.PacketHandler
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.HashedStack
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


object NMSPacketListener1_21_8 : Listener {

    lateinit var handler: PacketHandler
    private val players: HashMap<UUID, UUID> = hashMapOf()

    fun initialize(plugin: JavaPlugin, listener: PacketHandler) {
        this.handler = listener
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player as? CraftPlayer ?: return
        val id = UUID.randomUUID()
        players[player.uniqueId] = id

        val connection = player.internalConnection
        val channel = connection.channel
        val pipeline = channel.pipeline()

        pipeline.addBefore(
            "packet_handler",
            id.toString(),
            object : ChannelDuplexHandler() {
                override fun channelRead(channelHandlerContext: ChannelHandlerContext, packet: Any) {
                    if (packet is ServerboundContainerClosePacket) handler.onClose(PacketCloseInformation(player))

                    if (packet is ServerboundContainerClickPacket) {
                        if (packet.slotNum < 0) return

                        val hashedStack = packet.changedSlots[packet.slotNum.toInt()]
                        val itemFromHashedStack = (hashedStack as? HashedStack.ActualItem)?.item?.value()

                        handler.onClick(PacketClickInformation(
                            player,
                            packet.slotNum,
                            CraftItemStack.asNewCraftStack(itemFromHashedStack)
                        ))
                    }

                    super.channelRead(channelHandlerContext, packet)
                }
            }
        )
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val connection = event.player.internalConnection
        val channel = connection.channel

        val id = players[event.player.uniqueId]
        channel.eventLoop().submit {
            channel.pipeline().remove(id.toString())
        }
        players.remove(id)
    }

}