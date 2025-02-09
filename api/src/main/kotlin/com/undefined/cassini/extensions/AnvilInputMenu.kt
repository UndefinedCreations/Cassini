package com.undefined.cassini.extensions

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.data.pattern.MenuPattern
import com.undefined.cassini.impl.AnvilMenu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.lang.IndexOutOfBoundsException

abstract class AnvilInputMenu(
    title: Component,
  optimization: MenuOptimization = MenuOptimization.NORMAL,
  parent: Menu<*>? = null
) : AnvilMenu(title, optimization, parent) {

    constructor(title: String,
                optimization: MenuOptimization,
                parent: Menu<*>) : this(MiniMessage.miniMessage().deserialize(title), optimization, parent)

    open val leftItem: MenuItem<AnvilMenu> = MenuItem.fromMaterial(Material.AIR)
    open val rightItem: MenuItem<AnvilMenu> = MenuItem.fromMaterial(Material.AIR)
    open val outputItem: MenuItem<AnvilMenu> = MenuItem.fromMaterial(Material.AIR)

    override fun preinitialize(player: Player) = createInventory {
        setItem(0, leftItem)
        setItem(1, rightItem)
        setItem(2, outputItem)
    }

    override fun createResult(player: Player) {
        if (items[2] == null) setItem(2, (items[0]?.itemStack ?: ItemStack(Material.AIR)).clone())
    }

    override fun onClick(data: ClickData<AnvilMenu>) {
        try {
            onSlotClick(data, AnvilSlot.fromSlotNumber(data.slot))
        } catch (_: IndexOutOfBoundsException) {}
    }

    open fun onSlotClick(data: ClickData<AnvilMenu>, slot: AnvilSlot) {}

    class Builder(
        title: Component,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ): Menu.Builder<Builder, AnvilMenu>(title, 3, optimization, parent) {

        private var text: String? = null
        private var leftItem: MenuItem<AnvilMenu> = MenuItem.fromMaterial(Material.AIR)
        private var rightItem: MenuItem<AnvilMenu> = MenuItem.fromMaterial(Material.AIR)
        private var outputItem: MenuItem<AnvilMenu> = MenuItem.fromMaterial(Material.AIR)
        private var createResultActions: MutableList<AnvilMenu.() -> Unit> = mutableListOf()
        private var leftClickActions: MutableList<ClickData<AnvilMenu>.() -> Unit> = mutableListOf()
        private var rightClickActions: MutableList<ClickData<AnvilMenu>.() -> Unit> = mutableListOf()
        private var outputClickActions: MutableList<ClickData<AnvilMenu>.() -> Unit> = mutableListOf()

        constructor(
            title: String,
            optimization: MenuOptimization = MenuOptimization.NORMAL,
            parent: Menu<*>? = null
        ) : this(MiniMessage.miniMessage().deserialize(title), optimization, parent)

        private val patternList: MutableList<MenuPattern<AnvilInputMenu>> = mutableListOf()

        fun applyPattern(pattern: MenuPattern<AnvilInputMenu>): Builder = pattern.apply { patternList.add(pattern) }.let { this }

        fun onClick(slot: AnvilSlot, action: ClickData<AnvilMenu>.() -> Unit) = apply {
            when (slot) {
                AnvilSlot.LEFT -> leftClickActions.add(action)
                AnvilSlot.RIGHT -> rightClickActions.add(action)
                AnvilSlot.OUTPUT -> outputClickActions.add(action)
            }
        }

        fun leftItem(item: MenuItem<AnvilMenu>) = apply { leftItem = item }
        fun leftItem(item: ItemStack) = leftItem(MenuItem.fromItem(item))

        fun rightItem(item: MenuItem<AnvilMenu>) = apply { rightItem = item }
        fun rightItem(item: ItemStack) = rightItem(MenuItem.fromItem(item))

        fun outputItem(item: MenuItem<AnvilMenu>) = apply { outputItem = item }
        fun outputItem(item: ItemStack) = outputItem(MenuItem.fromItem(item))

        override fun build(): AnvilInputMenu =
            object : AnvilInputMenu(title, optimization, parent) {

                override val leftItem: MenuItem<AnvilMenu>
                    get() = this@Builder.leftItem
                override val rightItem: MenuItem<AnvilMenu>
                    get() = this@Builder.rightItem
                override val outputItem: MenuItem<AnvilMenu>
                    get() = this@Builder.outputItem

                override fun initialize(player: Player) {
                    for (initialization in initializations) initialization(player)
                    initMenu(this)
                    setCost(0)
                }
                override fun onSlotClick(data: ClickData<AnvilMenu>, slot: AnvilSlot) {
                    for (action in leftClickActions) data.action()
                    for (action in rightClickActions) data.action()
                    for (action in outputClickActions) data.action()
                }
                override fun onClick(data: ClickData<AnvilMenu>) {
                    super.onClick(data)
                    for (action in clickActions) data.action()
                }
                override fun onOpen(player: Player) {
                    for (action in openActions) player.action()
                }
                override fun onClose(player: Player) {
                    for (action in closeActions) player.action()
                }
            }
    }

}