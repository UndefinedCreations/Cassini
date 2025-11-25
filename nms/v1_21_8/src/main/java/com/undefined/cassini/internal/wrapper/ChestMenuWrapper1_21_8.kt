package com.undefined.cassini.internal.wrapper

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.internal.NMS1_21_8.serverPlayer
import net.kyori.adventure.text.Component
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ChestMenuWrapper1_21_8(
    val player: Player,
    override val type: MenuType,
    override var title: Component
) : ChestMenuWrapper {

    private val handle: CraftInventoryCustom = CraftInventoryCustom(player, type.size!!, title)
    override val id: Int = player.serverPlayer.containerMenu.containerId

    override fun setItem(slot: Int, item: ItemStack?) {
        handle.setItem(slot, item)
    }

    override fun open(player: Player) {
        player.openInventory(handle)
    }

}