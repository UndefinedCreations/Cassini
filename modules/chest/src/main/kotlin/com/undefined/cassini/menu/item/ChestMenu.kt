package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.ItemContainer
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component

class ChestMenu(
    title: Component,
    rows: Int,
    parent: CassiniMenu<*, *>?,
) : SingleContainerItemMenu<ChestMenu>(title, rows * MAX_WIDTH, parent) {

    override val rootContainer: ItemContainer = ItemContainer(0, 0, MAX_WIDTH, MAX_HEIGHT)

    companion object {
        const val MAX_WIDTH: Int = 9
        const val MAX_HEIGHT: Int = 6
    }

}