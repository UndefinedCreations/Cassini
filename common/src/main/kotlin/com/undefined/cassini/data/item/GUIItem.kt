package com.undefined.cassini.data.item

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.click.ClickActions
import org.bukkit.inventory.ItemStack

class GUIItem(val itemStack: ItemStack) {

    val actions: MutableSet<CassiniContext.() -> Unit> = mutableSetOf()

    fun addAction(action: CassiniContext.() -> Unit) {
        actions.add(action)
    }

    fun clearActions() {
        actions.clear()
    }

}