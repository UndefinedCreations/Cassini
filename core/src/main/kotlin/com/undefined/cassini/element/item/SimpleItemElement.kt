package com.undefined.cassini.element.item

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * A simple implementation of [ItemElement] that allows for the usage of items.
 */
class SimpleItemElement(private val item: (Player) -> ItemStack) : ItemElement() {
    constructor(item: ItemStack) : this({ item })
    constructor(material: Material) : this(ItemStack(material))

    override fun getItem(player: Player): ItemStack = item(player)
}