package com.undefined.cassini.v1_21_3

import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.exception.InvalidChestSizeException
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.minecraft.commands.CommandBuildContext
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.world.inventory.ChestMenu
import net.minecraft.world.inventory.ClickType
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.inventory.CraftItemStack

object MojangAdapter {

    private val COMMAND_BUILD_CONTEXT: CommandBuildContext by lazy {
        CommandBuildContext.simple(
            MinecraftServer.getServer().registryAccess(),
            MinecraftServer.getServer().worldData.dataConfiguration.enabledFeatures()
        )
    }

    val emptyItem: ItemStack = CraftItemStack.asNMSCopy(org.bukkit.inventory.ItemStack.empty())
    fun getMojangItemStack(item: GUIItem): ItemStack = CraftItemStack.asNMSCopy(item.itemStack)

    fun getComponent(component: net.kyori.adventure.text.Component): Component =
        Component.Serializer.fromJson(GsonComponentSerializer.gson().serializeToTree(component), COMMAND_BUILD_CONTEXT)!!

    fun getMenuType(size: Int): MenuType<ChestMenu> = when (size) {
        9 -> MenuType.GENERIC_9x1
        18 -> MenuType.GENERIC_9x2
        27 -> MenuType.GENERIC_9x3
        36 -> MenuType.GENERIC_9x4
        46 -> MenuType.GENERIC_9x5
        54 -> MenuType.GENERIC_9x6
        else -> throw InvalidChestSizeException()
    }

    fun getClickType(type: ClickType, button: Int, changedSlots: Int): org.bukkit.event.inventory.ClickType = when {
            type == ClickType.PICKUP && button == 0 -> org.bukkit.event.inventory.ClickType.LEFT
            type == ClickType.QUICK_MOVE && button == 0 -> org.bukkit.event.inventory.ClickType.SHIFT_LEFT
            type == ClickType.PICKUP && button == 1 -> org.bukkit.event.inventory.ClickType.RIGHT
            type == ClickType.QUICK_MOVE && button == 1 -> org.bukkit.event.inventory.ClickType.SHIFT_RIGHT
            type == ClickType.THROW && button == 0 && changedSlots == 0 -> org.bukkit.event.inventory.ClickType.WINDOW_BORDER_LEFT
            type == ClickType.THROW && button == 1 && changedSlots == 0 -> org.bukkit.event.inventory.ClickType.WINDOW_BORDER_RIGHT
            type == ClickType.CLONE && button == 2 -> org.bukkit.event.inventory.ClickType.MIDDLE
            type == ClickType.SWAP && button != 40 -> org.bukkit.event.inventory.ClickType.NUMBER_KEY
            type == ClickType.PICKUP_ALL -> org.bukkit.event.inventory.ClickType.DOUBLE_CLICK
            type == ClickType.THROW && button == 0 && changedSlots == 1 -> org.bukkit.event.inventory.ClickType.DROP
            type == ClickType.THROW && button == 1 -> org.bukkit.event.inventory.ClickType.CONTROL_DROP
            type == ClickType.SWAP -> org.bukkit.event.inventory.ClickType.SWAP_OFFHAND
            else -> org.bukkit.event.inventory.ClickType.UNKNOWN
        }

}