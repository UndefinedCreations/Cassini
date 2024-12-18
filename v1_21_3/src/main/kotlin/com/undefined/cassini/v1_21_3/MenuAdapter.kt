package com.undefined.cassini.v1_21_3

import com.undefined.cassini.exception.InvalidChestSizeException
import net.minecraft.world.inventory.ChestMenu
import net.minecraft.world.inventory.ClickType
import net.minecraft.world.inventory.MenuType

object MenuAdapter {

    fun getMenuType(size: Int): MenuType<ChestMenu> = when(size) {
        9 -> MenuType.GENERIC_9x1
        18 -> MenuType.GENERIC_9x2
        27 -> MenuType.GENERIC_9x3
        36 -> MenuType.GENERIC_9x4
        46 -> MenuType.GENERIC_9x5
        54 -> MenuType.GENERIC_9x6
        else -> throw InvalidChestSizeException()
    }

    fun getClickType(type: ClickType, button: Int, changedSlots: Int): org.bukkit.event.inventory.ClickType {
        if (type == ClickType.PICKUP && button == 0) return org.bukkit.event.inventory.ClickType.LEFT
        if (type == ClickType.QUICK_MOVE && button == 0) return org.bukkit.event.inventory.ClickType.SHIFT_LEFT
        if (type == ClickType.PICKUP && button == 1) return org.bukkit.event.inventory.ClickType.RIGHT
        if (type == ClickType.QUICK_MOVE && button == 1) return org.bukkit.event.inventory.ClickType.SHIFT_RIGHT
        if (type == ClickType.THROW && button == 0 && changedSlots == 0) return org.bukkit.event.inventory.ClickType.WINDOW_BORDER_LEFT
        if (type == ClickType.THROW && button == 1 && changedSlots == 0) return org.bukkit.event.inventory.ClickType.WINDOW_BORDER_RIGHT
        if (type == ClickType.CLONE && button == 2) return org.bukkit.event.inventory.ClickType.MIDDLE
        if (type == ClickType.SWAP && button != 40) return org.bukkit.event.inventory.ClickType.NUMBER_KEY
        if (type == ClickType.PICKUP_ALL) return org.bukkit.event.inventory.ClickType.DOUBLE_CLICK
        if (type == ClickType.THROW && button == 0 && changedSlots == 1) return org.bukkit.event.inventory.ClickType.DROP
        if (type == ClickType.THROW && button == 1 && changedSlots == 1) return org.bukkit.event.inventory.ClickType.CONTROL_DROP
        if (type == ClickType.SWAP) return org.bukkit.event.inventory.ClickType.SWAP_OFFHAND
        return org.bukkit.event.inventory.ClickType.UNKNOWN
    }

}