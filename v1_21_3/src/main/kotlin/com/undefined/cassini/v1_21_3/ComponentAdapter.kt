package com.undefined.cassini.v1_21_3

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.minecraft.commands.CommandBuildContext
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer

object ComponentAdapter {

    private val COMMAND_BUILD_CONTEXT: CommandBuildContext by lazy {
        CommandBuildContext.simple(
            MinecraftServer.getServer().registryAccess(),
            MinecraftServer.getServer().worldData.dataConfiguration.enabledFeatures()
        )
    }

    fun getComponent(component: net.kyori.adventure.text.Component): Component =
        Component.Serializer.fromJson(GsonComponentSerializer.gson().serializeToTree(component), COMMAND_BUILD_CONTEXT)!!
}