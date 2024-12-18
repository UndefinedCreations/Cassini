package com.undefined.cassini.v1_21_3

import com.undefined.cassini.exception.InvalidChestSizeException
import net.minecraft.world.inventory.ChestMenu
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

}