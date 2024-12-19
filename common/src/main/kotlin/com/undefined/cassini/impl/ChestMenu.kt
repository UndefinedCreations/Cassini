package com.undefined.cassini.impl

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import net.kyori.adventure.text.Component

abstract class ChestMenu(title: Component, size: Int, optimization: MenuOptimization = MenuOptimization.NORMAL, parent: Menu? = null) : Menu(title, size, optimization, parent) {
    fun createInventory(init: ChestMenu.() -> Unit) { init(this) }
}