package com.undefined.cassini.element.item

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A static implementation of [ItemElement] with [item].
 */
class StaticItemElement(val item: ItemStack) : ItemElement() {
    constructor(material: Material) : this(ItemStack(material))

    override fun getItem(player: Player): ItemStack = item
}