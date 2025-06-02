package com.undefined.cassini.internal

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.minecraft.commands.CommandBuildContext
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.kyori.adventure.text.Component as AdventureComponent
import net.minecraft.world.inventory.MenuType
import com.undefined.cassini.data.MenuType as CassiniMenuType

object MojangAdapter {

    private val COMMAND_BUILD_CONTEXT: CommandBuildContext by lazy {
        CommandBuildContext.simple(
            MinecraftServer.getServer().registryAccess(),
            MinecraftServer.getServer().worldData.dataConfiguration.enabledFeatures()
        )
    }

    fun getMojangMenuType(type: CassiniMenuType): MenuType<*> = when (type) {
        CassiniMenuType.CHEST_9X1 -> MenuType.GENERIC_9x1
        CassiniMenuType.CHEST_9X2 -> MenuType.GENERIC_9x2
        CassiniMenuType.CHEST_9X3 -> MenuType.GENERIC_9x3
        CassiniMenuType.CHEST_9X4 -> MenuType.GENERIC_9x4
        CassiniMenuType.CHEST_9X5 -> MenuType.GENERIC_9x5
        CassiniMenuType.CHEST_9X6 -> MenuType.GENERIC_9x6
        CassiniMenuType.DROPPER -> MenuType.GENERIC_3x3
        CassiniMenuType.ANVIL -> MenuType.ANVIL
    }

    fun getMojangComponent(component: AdventureComponent): Component = Component.Serializer.fromJson(GsonComponentSerializer.gson().serializeToTree(component), COMMAND_BUILD_CONTEXT)!!

}