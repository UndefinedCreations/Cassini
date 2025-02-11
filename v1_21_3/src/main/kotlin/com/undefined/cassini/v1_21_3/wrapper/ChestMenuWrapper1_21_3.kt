package com.undefined.cassini.v1_21_3.wrapper

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.wrapper.MenuWrapper
import com.undefined.cassini.v1_21_3.MojangAdapter
import com.undefined.cassini.v1_21_3.NMS1_21_3.serverPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.minecraft.world.inventory.ChestMenu
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@Suppress("ClassName")
class ChestMenuWrapper1_21_3(
    player: Player,
    override val size: Int,
    override val title: Component,
    override val config: MenuConfig
) : ChestMenu(
    MojangAdapter.getMenuType(size),
    player.serverPlayer().nextContainerCounter(),
    player.serverPlayer().inventory,
    CraftInventoryCustom(player, size, LegacyComponentSerializer.legacyAmpersand().serialize(title)).inventory, // just use CraftInventoryCustom(player, size, component) in paper
    size / 8
), MenuWrapper {
    override val id: Int = containerId
    override fun setItem(slot: Int, item: ItemStack) = setItem(slot, stateId, CraftItemStack.asNMSCopy(item))
}