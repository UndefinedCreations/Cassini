package com.undefined.cassini.data.item

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.click.ClickActions
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

open class GUIItem(val itemStack: ItemStack, vararg actions: CassiniContext.() -> Unit) {

    val actions: MutableSet<CassiniContext.() -> Unit> = actions.toMutableSet()

    fun addAction(action: CassiniContext.() -> Unit) {
        actions.add(action)
    }

    fun clearActions() {
        actions.clear()
    }

    companion object {
        fun fromItem(item: ItemStack): GUIItem = GUIItem(item)
        fun fromMaterial(material: Material): GUIItem = GUIItem(ItemStack(material))
    }

}