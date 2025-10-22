package com.undefined.cassini.data.item

import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player

class ClickData<T : ItemMenu<*>>(
    val menu: T,
    val player: Player,
    val slot: Int,
//    val type: ClickType, TODO add custom click type
) {

    var isCancelled: Boolean = false

    fun back() {
        // TODO
    }

    fun close() {
        // TODO
    }

    fun cancel() {
        isCancelled = true
    }

}