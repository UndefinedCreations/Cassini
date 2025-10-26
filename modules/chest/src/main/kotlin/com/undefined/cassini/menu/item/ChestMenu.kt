package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.ItemContainerImpl
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.menu.Menu
import net.kyori.adventure.text.Component

abstract class ChestMenu(
    title: Component,
    rows: Int,
    parent: Menu<*, *>? = null,
    override val settings: ItemMenuSettings = ItemMenuSettings(),
    rootContainer: ItemContainerImpl = ItemContainerImpl(0, 0, MAX_WIDTH, MAX_HEIGHT)
) : SingleContainerItemMenu<ChestMenu>(
    title = title,
    size = rows * MAX_WIDTH,
    parent = parent,
    type = MenuType.rowsToChestMenu(rows),
    maxWidth = MAX_WIDTH,
    rootContainer = rootContainer,
) {

    init {
        rootContainer.menu = this
    }

    companion object {
        const val MAX_WIDTH: Int = 9
        const val MAX_HEIGHT: Int = 6
    }

}