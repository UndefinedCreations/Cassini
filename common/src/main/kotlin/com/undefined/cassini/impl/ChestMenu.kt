package com.undefined.cassini.impl

import com.undefined.cassini.Menu
import net.kyori.adventure.text.Component

abstract class ChestMenu(title: Component, size: Int, parent: Menu? = null) : Menu(title, size, parent) {
    fun createInventory(init: ChestMenu.() -> Unit) { init(this) }
}