package com.undefined.cassini.v1_21_3

import net.minecraft.network.protocol.Packet
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.craftPlayer(): CraftPlayer = player as CraftPlayer
fun Player.serverPlayer(): ServerPlayer = craftPlayer().handle
fun Player.connection(): ServerGamePacketListenerImpl = serverPlayer().connection
fun Player.sendPacket(packet: Packet<*>) = connection().sendPacket(packet)