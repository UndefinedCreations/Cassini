package com.undefined.cassini.impl

import com.undefined.cassini.Cassini
import com.undefined.cassini.ContainerMenu
import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.data.pattern.MenuPattern
import com.undefined.cassini.nms.wrapper.AnvilMenuWrapper
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus.OverrideOnly

abstract class AnvilMenu(
    title: Component,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu<*>? = null
) : ContainerMenu<AnvilMenu>(title, 3, optimization, parent) {

    var cost: Int? = null
        private set
    var text: String = ""
        get() = getWrapper<AnvilMenuWrapper>()?.text!!
        set(value) {
            field = value
            getWrapper<AnvilMenuWrapper>()?.text = value
        }

    constructor(
        title: String,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ) : this(Cassini.miniMessage.deserialize(title), optimization, parent)

    fun applyPattern(pattern: MenuPattern<AnvilMenu>) = pattern.apply(this)

    fun setInputLeftItem(item: MenuItem<AnvilMenu>) = setItem(0, item)
    fun setInputLeftItem(item: ItemStack) = setItem(0, item)

    fun setInputRightItem(item: MenuItem<AnvilMenu>) = setItem(1, item)
    fun setInputRightItem(item: ItemStack) = setItem(1, item)

    fun setCost(cost: Int?) {
        this.cost = cost
        cost?.let { getWrapper<AnvilMenuWrapper>()?.itemCost = cost }
    }

    @OverrideOnly
    open fun createResult(player: Player) {
        // should be overridden
    }

    class Builder(
        title: Component,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ): ContainerMenu.Builder<Builder, AnvilMenu>(title, 3, optimization, parent) {

        private var text: String? = null
        private var cost: Int? = null
        private var createResultActions: MutableList<AnvilMenu.() -> Unit> = mutableListOf()

        constructor(
            title: String,
            optimization: MenuOptimization = MenuOptimization.NORMAL,
            parent: Menu<*>? = null
        ) : this(Cassini.miniMessage.deserialize(title), optimization, parent)

        private val patternList: MutableList<MenuPattern<ChestMenu>> = mutableListOf()

        fun applyPattern(pattern: MenuPattern<ChestMenu>): Builder = pattern.apply { patternList.add(pattern) }.let { this }

        fun setInputLeftItem(item: MenuItem<AnvilMenu>) = setItem(0, item)
        fun setInputLeftItem(item: ItemStack) = setItem(0, item)

        fun setInputRightItem(item: MenuItem<AnvilMenu>) = setItem(1, item)
        fun setInputRightItem(item: ItemStack) = setItem(1, item)

        fun setCost(cost: Int?) = apply {
            this.cost = cost
        }

        fun createResult(action: AnvilMenu.() -> Unit) = apply {
            createResultActions.add(action)
        }

        override fun build(): AnvilMenu =
            object : AnvilMenu(title, optimization, parent) {
                override fun initialize(player: Player) {
                    for (initialization in initializations) initialization(player)
                    initMenu(this)
                    setCost(cost)
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
                override fun createResult(player: Player) {
                    if (createResultActions.isEmpty()) return super.createResult(player)
                    for (action in createResultActions) action(this)
                }
            }
    }

}