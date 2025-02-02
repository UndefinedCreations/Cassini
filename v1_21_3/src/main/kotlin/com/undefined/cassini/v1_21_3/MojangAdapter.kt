package com.undefined.cassini.v1_21_3

import com.undefined.cassini.Menu
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.exception.InvalidChestSizeException
import com.undefined.cassini.exception.InvalidMenuTypeException
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.minecraft.commands.CommandBuildContext
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player

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

    fun getMenuType(menu: Menu): MenuType<*> = when (menu) {
        is com.undefined.cassini.impl.ChestMenu -> when (menu.size) {
            9 -> MenuType.GENERIC_9x1
            18 -> MenuType.GENERIC_9x2
            27 -> MenuType.GENERIC_9x3
            36 -> MenuType.GENERIC_9x4
            46 -> MenuType.GENERIC_9x5
            54 -> MenuType.GENERIC_9x6
            else -> throw InvalidChestSizeException()
        }
        is com.undefined.cassini.impl.AnvilMenu -> MenuType.ANVIL
        else -> throw InvalidMenuTypeException()
    }

    fun getClickType(type: ClickType, button: Int, changedSlots: Int): org.bukkit.event.inventory.ClickType =
        when {
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

    fun getContainer(menu: Menu, type: MenuType<*>, id: Int, player: Player): AbstractContainerMenu = when (menu) {
        is com.undefined.cassini.impl.ChestMenu -> ChestMenu(type, id, player.serverPlayer().inventory, CraftInventoryCustom(player, menu.size, menu.title).inventory, menu.size / 8)
        is com.undefined.cassini.impl.AnvilMenu -> AnvilMenu(id, player.serverPlayer().inventory)
        else -> throw InvalidMenuTypeException()
    }

}