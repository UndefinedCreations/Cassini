package com.undefined.cassini.internal.info

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class PacketClickInformation(val player: Player, val slot: Short, val newItemInSlot: ItemStack)