package com.undefined.cassini.impl

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.data.pattern.MenuPattern
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

abstract class AnvilMenu(
    title: Component,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu? = null
) : Menu(title, 3, optimization, parent) {

    var outputItem: GUIItem? = null
        private set
    var text: String? = null
        private set

    constructor(
        title: String,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu? = null
    ) : this(Component.text(title), optimization, parent)

    fun createInventory(init: AnvilMenu.() -> Unit) { this.init() }

    fun applyPattern(pattern: MenuPattern<AnvilMenu>) = pattern.apply(this)

    fun setInputLeftItem(item: GUIItem) = setItem(0, item)
    fun setInputLeftItem(item: ItemStack) = setItem(0, item)

    fun setInputRightItem(item: GUIItem) = setItem(1, item)
    fun setInputRightItem(item: ItemStack) = setItem(1, item)

    fun setOutputItem(item: GUIItem) {
        outputItem = item
    }
    fun setOutputItem(item: ItemStack) = setOutputItem(GUIItem.fromItem(item))

    fun setText(text: String) {
        this.text = text
    }

}