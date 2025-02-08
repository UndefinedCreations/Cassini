package com.undefined.cassini.impl

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.data.pattern.MenuPattern
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class AnvilMenu(
    title: Component,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu<*>? = null
) : Menu<AnvilMenu>(title, 3, optimization, parent) {

    var text: String? = null
        private set

    constructor(
        title: String,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ) : this(MiniMessage.miniMessage().deserialize(title), optimization, parent)

    fun createInventory(init: AnvilMenu.() -> Unit) { this.init() }

    fun applyPattern(pattern: MenuPattern<AnvilMenu>) = pattern.apply(this)

    fun setInputLeftItem(item: MenuItem<AnvilMenu>) = setItem(0, item)
    fun setInputLeftItem(item: ItemStack) = setItem(0, item)

    fun setInputRightItem(item: MenuItem<AnvilMenu>) = setItem(1, item)
    fun setInputRightItem(item: ItemStack) = setItem(1, item)

    fun setOutputItem(item: MenuItem<AnvilMenu>) = setItem(2, item)
    fun setOutputItem(item: ItemStack) = setItem(2, item)

    fun setText(text: String?) {
        this.text = text
    }

    class Builder(
        title: Component,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ): Menu.Builder<Builder, AnvilMenu>(title, 3, optimization, parent) {

        var text: String? = null
            private set

        constructor(
            title: String,
            optimization: MenuOptimization = MenuOptimization.NORMAL,
            parent: Menu<*>? = null
        ) : this(MiniMessage.miniMessage().deserialize(title), optimization, parent)

        private val patternList: MutableList<MenuPattern<ChestMenu>> = mutableListOf()

        fun applyPattern(pattern: MenuPattern<ChestMenu>): Builder = pattern.apply { patternList.add(pattern) }.let { this }

        fun setInputLeftItem(item: MenuItem<AnvilMenu>) = setItem(0, item)
        fun setInputLeftItem(item: ItemStack) = setItem(0, item)

        fun setInputRightItem(item: MenuItem<AnvilMenu>) = setItem(1, item)
        fun setInputRightItem(item: ItemStack) = setItem(1, item)

        fun setOutputItem(item: MenuItem<AnvilMenu>) = setItem(2, item)
        fun setOutputItem(item: ItemStack) = setItem(2, item)

        fun setText(text: String?) = apply {
            this.text = text
        }

        override fun build(): AnvilMenu =
            object : AnvilMenu(title, optimization, parent) {
                override fun initialize(player: Player) {
                    for (initialization in initializations) initialization(player)
                    initMenu(this)
                    setText(text)
                }
                override fun onClick(data: ClickData<AnvilMenu>) {
                    for (action in clickActions) action(data)
                }
                override fun onOpen(player: Player) {
                    for (action in openActions) action(player)
                }
                override fun onClose(player: Player) {
                    for (action in closeActions) action(player)
                }
            }
    }

}