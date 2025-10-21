package com.undefined.cassini.internal

import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.mojang.serialization.JsonOps
import io.papermc.paper.command.brigadier.PaperCommands
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.minecraft.commands.CommandBuildContext
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.MinecraftServer
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.inventory.CraftItemStack
import com.undefined.cassini.data.MenuType as CassiniMenuType
import net.kyori.adventure.text.Component as AdventureComponent
import org.bukkit.inventory.ItemStack as BukkitItemStack

object MojangAdapter {

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

    fun getMojangComponent(component: AdventureComponent): Component =
        ComponentSerializer.fromJson(GsonComponentSerializer.gson().serializeToTree(component), PaperCommands.INSTANCE.buildContext)

    fun getItems(bukkitItems: List<BukkitItemStack>): NonNullList<ItemStack> = NonNullList.create<ItemStack>().apply {
        for (item in bukkitItems) add(CraftItemStack.asNMSCopy(item))
    }

    object ComponentSerializer {
        fun deserialize(json: JsonElement, provider: HolderLookup.Provider): MutableComponent =
            ComponentSerialization.CODEC.parse(provider.createSerializationContext(JsonOps.INSTANCE), json)
                .getOrThrow { JsonParseException(it) } as MutableComponent

        fun fromJson(json: JsonElement, registries: HolderLookup.Provider): MutableComponent = deserialize(json, registries)
    }

}