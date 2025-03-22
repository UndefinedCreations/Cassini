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
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import org.jetbrains.annotations.ApiStatus.OverrideOnly

abstract class SmithingMenu(
    title: Component,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu<*>? = null
) : ContainerMenu<SmithingMenu>(title, 3, optimization, parent) {

    constructor(
        title: String,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ) : this(Cassini.miniMessage.deserialize(title), optimization, parent)

    fun applyPattern(pattern: MenuPattern<SmithingMenu>) = pattern.apply(this)

    fun setTrimPattern(item: MenuItem<SmithingMenu>) = setItem(0, item)
    fun setTrimPattern(pattern: TrimPattern) = setItem(0, ItemStack(Material.matchMaterial(pattern.key.toString()) ?: throw IllegalArgumentException("Could not find a material with the given trim pattern!")))

    fun setInputItem(item: MenuItem<SmithingMenu>) = setItem(1, item)
    fun setInputItem(item: ItemStack) = setItem(1, item)

    fun setTrimMaterial(item: MenuItem<SmithingMenu>) = setItem(1, item)
    fun setTrimMaterial(material: TrimMaterial) = setItem(1, ItemStack(Material.matchMaterial(material.key.toString()) ?: throw IllegalArgumentException("Could not find a material with the given trim pattern!")))

    @OverrideOnly
    open fun createResult(player: Player) {
        // should be overridden
    }

    class Builder(
        title: Component,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ): ContainerMenu.Builder<Builder, SmithingMenu>(title, 3, optimization, parent) {

        private var createResultActions: MutableList<SmithingMenu.() -> Unit> = mutableListOf()

        constructor(
            title: String,
            optimization: MenuOptimization = MenuOptimization.NORMAL,
            parent: Menu<*>? = null
        ) : this(Cassini.miniMessage.deserialize(title), optimization, parent)

        private val patternList: MutableList<MenuPattern<ChestMenu>> = mutableListOf()

        fun applyPattern(pattern: MenuPattern<ChestMenu>): Builder = pattern.apply { patternList.add(pattern) }.let { this }

        fun setTrimPattern(item: MenuItem<SmithingMenu>) = setItem(0, item)
        fun setTrimPattern(pattern: TrimPattern) = setItem(0, ItemStack(Material.matchMaterial(pattern.key.toString()) ?: throw IllegalArgumentException("Could not find a material with the given trim pattern!")))

        fun setInputItem(item: MenuItem<SmithingMenu>) = setItem(1, item)
        fun setInputItem(item: ItemStack) = setItem(1, item)

        fun setTrimMaterial(item: MenuItem<SmithingMenu>) = setItem(1, item)
        fun setTrimMaterial(material: TrimMaterial) = setItem(1, ItemStack(Material.matchMaterial(material.key.toString()) ?: throw IllegalArgumentException("Could not find a material with the given trim pattern!")))

        fun createResult(action: SmithingMenu.() -> Unit) = apply {
            createResultActions.add(action)
        }

        override fun build(): SmithingMenu =
            object : SmithingMenu(title, optimization, parent) {
                override fun initialize(player: Player) {
                    for (initialization in initializations) initialization(player)
                    initMenu(this)
                }
                override fun onClick(data: ClickData<SmithingMenu>) {
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