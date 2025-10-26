package com.undefined.cassini.element.item

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * An implementation of [ItemElement] that gets an item from a synchronous function.
 */
class SyncItemElement(private val item: (Player) -> ItemStack) : ItemElement() {
    constructor(item: ItemStack) : this({ item })
    constructor(material: Material) : this(ItemStack(material))

    override fun getItem(player: Player): ItemStack = item(player)
}