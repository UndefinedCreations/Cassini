package com.undefined.cassini.data.item

import com.undefined.cassini.data.click.ClickActionType
import com.undefined.cassini.data.click.ClickData
import org.bukkit.inventory.ItemStack

class GUIItem(val itemStack: ItemStack) {

    val actions: MutableSet<ClickActionType> = mutableSetOf()
    val customActions: MutableList<(ClickData) -> Unit> = mutableListOf()

    fun addAction(action: ClickActionType) {
        actions.add(action)
    }

    fun addAction(action: (ClickData) -> Unit) {
        customActions.add(action)
    }

    fun clearActions() {
        customActions.clear()
    }

}