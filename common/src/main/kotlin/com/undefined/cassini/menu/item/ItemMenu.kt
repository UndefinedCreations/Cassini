package com.undefined.cassini.menu.item

import com.undefined.cassini.menu.item.ItemMenuSettings
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component

/**
 * Represents a menu that contains items.
 */
abstract class ItemMenu<T : ItemMenu<T>>(
    title: Component,
    val size: Int,
    parent: CassiniMenu<*, *>?,
) : CassiniMenu<T, ItemMenuSettings>(title, parent) {

    override val settings: ItemMenuSettings = ItemMenuSettings()

}