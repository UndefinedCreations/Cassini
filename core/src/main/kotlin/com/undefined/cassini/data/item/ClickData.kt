package com.undefined.cassini.data.item

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ItemMenu
import com.undefined.cassini.util.closeMenu
import com.undefined.cassini.util.openMenu
import org.bukkit.entity.Player

class ClickData<T : ItemMenu<*>>(
    val menu: T,
    val player: Player,
    val slot: Int,
//    val type: ClickType, TODO add custom click type
) {

    val element: ItemElement?
        get() = menu.elements[slot]
    var isCancelled: Boolean = false

    fun back() {
        menu.parent?.let { player.openMenu(it) } ?: player.closeMenu()
    }

    fun close() {
        player.closeMenu()
    }

    fun cancel() {
        isCancelled = true
    }

}