package com.undefined.cassini.data.item

import com.undefined.cassini.Menu
import com.undefined.cassini.data.click.ClickAction
import com.undefined.cassini.data.click.ClickData
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MenuItem<T : Menu<T>>(val itemStack: ItemStack, vararg actions: ClickData<T>.() -> Unit) {

    val actions: MutableSet<ClickData<T>.() -> Unit> = actions.toMutableSet()

    fun addAction(action: ClickAction) {
        actions.add(action.action)
    }

    fun addAction(action: ClickData<T>.() -> Unit) {
        actions.add(action)
    }

    fun clearActions() = actions.clear()

    companion object {
        fun <T : Menu<T>> fromItem(item: ItemStack): MenuItem<T> = MenuItem(item)
        fun <T : Menu<T>> fromMaterial(material: Material): MenuItem<T> = fromItem(ItemStack(material))
    }

}