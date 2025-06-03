package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.ItemContainer
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.internal.NMS
import com.undefined.cassini.menu.CassiniMenu
import com.undefined.cassini.nms.NMSManager
import net.kyori.adventure.text.Component

abstract class ChestMenu(
    title: Component,
    rows: Int,
    parent: CassiniMenu<*, *>? = null,
    override val settings: ItemMenuSettings = ItemMenuSettings(),
) : SingleContainerItemMenu<ChestMenu>(title, rows * MAX_WIDTH, parent, MenuType.rowsToChestMenu(rows), MAX_WIDTH) {

    override val nms: NMS = NMSManager.nms
    override val rootContainer: ItemContainer = ItemContainer(0, 0, MAX_WIDTH, MAX_HEIGHT)

    companion object {
        const val MAX_WIDTH: Int = 9
        const val MAX_HEIGHT: Int = 6
    }

}