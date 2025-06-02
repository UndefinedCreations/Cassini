package com.undefined.cassini.menu.item

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.internal.NMS
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

/**
 * Represents a menu that contains items.
 */
abstract class ItemMenu<T : ItemMenu<T>>(
    title: Component,
    val size: Int,
    parent: CassiniMenu<*, *>?,
    val type: MenuType,
) : CassiniMenu<T, ItemMenuSettings>(title, parent) {

    abstract val nms: NMS
    override val settings: ItemMenuSettings = ItemMenuSettings()

    override fun open(player: Player) {
        super.open(player)

        nms.sendOpenScreenPacket(player, type, title)
    }
}