package com.undefined.cassini

import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickAction
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.item.MenuItem
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

abstract class Menu<T : Menu<T>>(var title: Component, val size: Int, val optimization: MenuOptimization, val parent: Menu<*>?) {

    private var hasBeenInitialized = false
    open val items: HashMap<Int, MenuItem<T>> = hashMapOf()
    fun firstEmptySlot(): Int {
        if (size == items.size) return -1
        return items.size + 1
    }

    @ApiStatus.OverrideOnly
    open fun preinitialize(player: Player) {}

    @ApiStatus.OverrideOnly
    abstract fun initialize(player: Player)

    @ApiStatus.OverrideOnly
    open fun afterinitialize(player: Player) {}

    fun setItem(slot: Int, item: ItemStack) {
        items[slot] = MenuItem(item)
    }

    fun setItem(slot: Int, item: MenuItem<T>) {
        items[slot] = item
    }

    fun addItem(item: ItemStack) {
        if (size == items.size) return
        items[firstEmptySlot()] = MenuItem(item)
    }

    fun addItem(item: MenuItem<T>) {
        if (size == items.size) return
        items[firstEmptySlot()] = item
    }

    // Set multiple items methods
    fun setItems(item: MenuItem<T>, slots: List<Int>) {
        for (slot in slots) setItem(slot, item)
    }
    fun setItems(item: MenuItem<T>, vararg slots: Int) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, vararg slots: Int) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, slots: List<Int>) = setItems(MenuItem(item), slots)
    fun setItems(item: MenuItem<T>, slots: IntRange) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, slots: IntRange) = setItems(item, slots.toList())

    fun setItems(item: MenuItem<T>, action: ClickAction, slots: List<Int>) {
        for (slot in slots) setItem(slot, item.apply { addAction(action) })
    }
    fun setItems(item: MenuItem<T>, action: ClickAction, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, slots: List<Int>) = setItems(MenuItem(item), action, slots)
    fun setItems(item: MenuItem<T>, action: ClickAction, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, slots: IntRange) = setItems(item, action, slots.toList())

    fun setItems(item: MenuItem<T>, action: ClickData<T>.() -> Unit, slots: List<Int>) {
        for (slot in slots) setItem(slot, item.apply { addAction(action) })
    }
    fun setItems(item: MenuItem<T>, action: ClickData<T>.() -> Unit, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickData<T>.() -> Unit, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickData<T>.() -> Unit, slots: List<Int>) = setItems(MenuItem(item), action, slots)
    fun setItems(item: MenuItem<T>, action: ClickData<T>.() -> Unit, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickData<T>.() -> Unit, slots: IntRange) = setItems(item, action, slots.toList())

    fun isSlotEmpty(slot: Int) = !items.containsKey(slot)
    fun clear() = items.clear()

    @ApiStatus.OverrideOnly
    open fun onClick(data: ClickData<T>) {}

    @ApiStatus.OverrideOnly
    open fun onOpen(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun onClose(player: Player) {}

    @Suppress("UNCHECKED_CAST")
    abstract class Builder<T, R : Menu<R>>(
        var title: Component,
        val size: Int,
        val optimization: MenuOptimization,
        val parent: Menu<*>?
    ) {

        protected var items: HashMap<Int, MenuItem<R>> = hashMapOf()

        protected var initializations: MutableList<Player.() -> (Unit)> = mutableListOf()
        protected var clickActions: MutableList<ClickData<R>.() -> (Unit)> = mutableListOf()
        protected var openActions: MutableList<Player.() -> (Unit)> = mutableListOf()
        protected var closeActions: MutableList<Player.() -> (Unit)> = mutableListOf()

        fun initialize(initialize: Player.() -> (Unit)): T = apply { initializations.add(initialize) } as T
        fun onClick(data: ClickData<R>.() -> (Unit)): T = apply { clickActions.add(data) } as T
        fun onOpen(open: Player.() -> (Unit)): T = apply { openActions.add(open) } as T
        fun onClose(close: Player.() -> (Unit)): T = apply { closeActions.add(close) } as T

        fun setItem(slot: Int, item: MenuItem<R>): T {
            items[slot] = item
            return this as T
        }
        fun setItem(slot: Int, item: ItemStack): T = setItem(slot, MenuItem.fromItem(item))
        fun setItems(item: MenuItem<R>, slots: List<Int>): T {
            for (slot in slots) setItem(slot, item)
            return this as T
        }
        fun setItems(item: MenuItem<R>, vararg slots: Int): T = setItems(item, slots.toList())
        fun setItems(item: ItemStack, vararg slots: Int): T = setItems(item, slots.toList())
        fun setItems(item: ItemStack, slots: List<Int>): T = setItems(MenuItem(item), slots)
        fun setItems(item: MenuItem<R>, slots: IntRange): T = setItems(item, slots.toList())
        fun setItems(item: ItemStack, slots: IntRange): T = setItems(item, slots.toList())

        fun setItems(item: MenuItem<R>, action: ClickAction, slots: List<Int>): T {
            for (slot in slots) setItem(slot, item.apply { addAction(action) })
            return this as T
        }
        fun setItems(item: MenuItem<R>, action: ClickAction, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickAction, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickAction, slots: List<Int>): T = setItems(MenuItem(item), action, slots)
        fun setItems(item: MenuItem<R>, action: ClickAction, slots: IntRange): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickAction, slots: IntRange): T = setItems(item, action, slots.toList())

        fun setItems(item: MenuItem<R>, action: ClickData<R>.() -> Unit, slots: List<Int>): T {
            for (slot in slots) setItem(slot, item.apply { addAction(action) })
            return this as T
        }
        fun setItems(item: MenuItem<R>, action: ClickData<R>.() -> Unit, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickData<R>.() -> Unit, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickData<R>.() -> Unit, slots: List<Int>): T = setItems(MenuItem(item), action, slots)
        fun setItems(item: MenuItem<R>, action: ClickData<R>.() -> Unit, slots: IntRange): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickData<R>.() -> Unit, slots: IntRange): T = setItems(item, action, slots.toList())

        fun addItem(item: ItemStack): T = addItem(MenuItem.fromItem(item))
        fun addItem(item: MenuItem<R>): T = apply { items[firstEmptySlot()] = item } as T

        protected fun initMenu(menu: R) {
            for (item in items) menu.setItem(item.key, item.value)
        }

        private fun firstEmptySlot(): Int {
            if (size == items.size) return -1
            return items.size + 1
        }

        abstract fun build(): R
    }
}