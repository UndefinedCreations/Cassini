package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.ItemContainer
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component


/**
 * An [ItemMenu] that only contains one root container.
 */
@Suppress("UNCHECKED_CAST")
abstract class SingleContainerItemMenu<T : SingleContainerItemMenu<T>>(
    title: Component,
    size: Int,
    parent: CassiniMenu<*, *>?,
) : ItemMenu<T>(title, size, parent) {

    abstract val rootContainer: ItemContainer

    // BASE
    fun addContainer(container: ItemContainer): T = apply {
        rootContainer
    } as T

}