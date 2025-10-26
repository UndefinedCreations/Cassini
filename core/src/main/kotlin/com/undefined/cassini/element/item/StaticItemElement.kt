package com.undefined.cassini.element.item

import com.undefined.cassini.data.item.ClickData
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A static implementation of [ItemElement] with [item].
 *
 * @param action Action to automatically add to the element.
 */
class StaticItemElement(val item: ItemStack, action: ClickData<*>.() -> Unit = {}) : ItemElement() {
    constructor(material: Material, action: ClickData<*>.() -> Unit = {}) : this(ItemStack(material), action)

    init {
        addAction(action)
    }

    override fun getItem(player: Player): ItemStack = item
}