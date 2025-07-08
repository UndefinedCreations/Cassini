package com.undefined.cassini.internal

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.serialization.JsonOps
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.internal.listener.PacketHandler
import net.kyori.adventure.text.Component
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.UuidArgument
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.dialog.Dialog
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerCommonPacketListenerImpl
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.v1_21_R5.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_21_R5.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.inventory.ItemStack as BukkitItemStack
import org.jetbrains.annotations.ApiStatus
import java.util.UUID

@ApiStatus.Internal
object NMS1_21_7 : NMS {

    override fun sendOpenScreenPacket(player: Player, type: MenuType, title: Component) {
        player.serverPlayer.connection.send(ClientboundOpenScreenPacket(
            CommonMenuManager.CONTAINER_ID,
            MojangAdapter.getMojangMenuType(type),
            MojangAdapter.getMojangComponent(title)
        ))
    }

    override fun sendContentsPacket(player: Player, contents: List<BukkitItemStack>) {
        player.serverPlayer.connection.send(ClientboundContainerSetContentPacket(
            CommonMenuManager.CONTAINER_ID,
            CommonMenuManager.SYNC_ID,
            MojangAdapter.getItems(contents),
            CommonMenuManager.carriedItems[player.uniqueId]?.let { CraftItemStack.asNMSCopy(it) } ?: ItemStack.EMPTY
        ))
    }

    override fun getContainerId(player: Player): Int = player.serverPlayer.nextContainerCounter()

    override fun initializePacketListener(plugin: JavaPlugin, listener: PacketHandler) = NMSPacketListener1_21_7.initialize(plugin, listener)

    override fun encodeItemStack(item: BukkitItemStack): JsonElement =
        ItemStack.CODEC.encode(CraftItemStack.asNMSCopy(item), JsonOps.INSTANCE, JsonObject()).result().get()

    override fun registerDialogCommand(players: Map<UUID, *>) {
        val command = LiteralArgumentBuilder.literal<CommandSourceStack>("_cassini_dialog")
        command.requires { source ->
            if (!source.isPlayer) return@requires false
            source.playerOrException.uuid in players.keys
        }
        command.then(
            LiteralArgumentBuilder.literal<CommandSourceStack>("click").then(
                RequiredArgumentBuilder.argument<CommandSourceStack, UUID>("button", UuidArgument.uuid()).then(
                    RequiredArgumentBuilder.argument<CommandSourceStack, String>("info", StringArgumentType.greedyString()) // additional information
                        .executes { context ->
                            if (!context.source.isPlayer) return@executes Command.SINGLE_SUCCESS
                            val uuid = UuidArgument.getUuid(context, "button")
                            NMSPacketListener1_21_7.handler.onDialogClick(context.source.bukkitSender as Player, uuid)
                            Command.SINGLE_SUCCESS
                        }
                )
            )
        )
        val dispatcher = MinecraftServer.getServer().vanillaCommandDispatcher.dispatcher
        dispatcher.register(command)
    }

    override fun showDialog(player: Player, json: JsonElement) {
        val serverPlayer = player.serverPlayer
        serverPlayer.openDialog(Dialog.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow { JsonParseException(it) })
    }

    val Player.serverPlayer: ServerPlayer
        get() = (player as CraftPlayer).handle
    val Player.connection: Connection
        get() = ServerCommonPacketListenerImpl::class.java.getDeclaredField("e").apply { isAccessible = true }.get(serverPlayer.connection) as Connection // VERCHECK - connection

}