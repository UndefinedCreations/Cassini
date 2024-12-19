package com.undefined.cassini.v1_21_3

import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

fun Player.craftPlayer(): CraftPlayer = player as CraftPlayer
fun Player.serverPlayer(): ServerPlayer = craftPlayer().handle
fun Player.connection(): ServerGamePacketListenerImpl = serverPlayer().connection

fun sync(runnable: () -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            runnable()
        }
    }.runTask(PacketListener.plugin)
}