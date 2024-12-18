package com.undefined.cassini.v1_21_3

import com.undefined.cassini.data.item.GUIItem
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.inventory.CraftItemStack

object ItemAdapter {
    val empty: ItemStack = CraftItemStack.asNMSCopy(org.bukkit.inventory.ItemStack.empty())
    fun getMojangItemStack(item: GUIItem): ItemStack = CraftItemStack.asNMSCopy(item.itemStack)
}