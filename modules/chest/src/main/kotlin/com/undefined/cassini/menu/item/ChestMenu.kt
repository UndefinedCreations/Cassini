package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.ItemContainer
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.UUID

abstract class ChestMenu(
    title: Component,
    rows: Int,
    parent: CassiniMenu<*, *>? = null,
    override val settings: ItemMenuSettings = ItemMenuSettings(),
) : SingleContainerItemMenu<ChestMenu>(title, rows * MAX_WIDTH, parent, MenuType.rowsToChestMenu(rows), MAX_WIDTH) {

    override val rootContainer: ItemContainer = ItemContainer(this, 0, 0, MAX_WIDTH, MAX_HEIGHT)

    companion object {
        const val MAX_WIDTH: Int = 9
        const val MAX_HEIGHT: Int = 6
    }

}