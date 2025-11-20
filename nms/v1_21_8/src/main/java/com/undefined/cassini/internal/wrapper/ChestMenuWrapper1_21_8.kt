package com.undefined.cassini.internal.wrapper

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.internal.MojangAdapter
import com.undefined.cassini.internal.NMS1_21_8.serverPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.minecraft.world.inventory.ChestMenu
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ChestMenuWrapper1_21_8(
    player: Player,
    override val type: MenuType,
    override var title: Component,
) : ChestMenu(
    MojangAdapter.getMojangMenuType(type),
    player.serverPlayer.nextContainerCounter(),
    player.serverPlayer.inventory,
    CraftInventoryCustom(player, type.size!!, title).inventory, // just use CraftInventoryCustom(player, size, component) in paper
    type.size!! / 8
), ChestMenuWrapper {

    override val id: Int = containerId

    override fun setItem(slot: Int, item: ItemStack) {
        setItem(slot, stateId, CraftItemStack.asNMSCopy(item))
    }

    override fun clearItems() {
        for (i in 0 until type.size!!) setItem(i, ItemStack.empty())
    }

}