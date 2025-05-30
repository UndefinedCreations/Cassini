package com.undefined.cassini.data.item

import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MenuItem<T : ItemMenu<*>>(val itemStack: ItemStack, vararg actions: ClickData<T>.() -> Unit) {

    val actions: MutableSet<ClickData<T>.() -> Unit> = actions.toMutableSet()

    fun addAction(action: ClickAction) {
        actions.add(action.action)
    }

    fun addAction(action: ClickData<T>.() -> Unit) {
        actions.add(action)
    }

    fun clearActions() = actions.clear()

    companion object {
        fun <T : ItemMenu<T>> fromItem(item: ItemStack): MenuItem<T> = MenuItem(item)
        fun <T : ItemMenu<T>> fromMaterial(material: Material): MenuItem<T> = fromItem(ItemStack(material))
    }

}