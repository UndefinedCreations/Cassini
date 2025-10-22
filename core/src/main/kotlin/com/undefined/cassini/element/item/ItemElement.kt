package com.undefined.cassini.element.item

import com.undefined.cassini.element.ClickableElement
import com.undefined.cassini.menu.item.ItemMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents an element in an [ItemMenu].
 */
abstract class ItemElement : ClickableElement() {
    abstract fun getItem(player: Player): ItemStack
}