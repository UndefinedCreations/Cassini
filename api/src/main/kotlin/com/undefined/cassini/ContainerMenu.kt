package com.undefined.cassini

import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickAction
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.nms.wrapper.MenuWrapper
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

abstract class ContainerMenu<T : ContainerMenu<T>>(
    title: Component,
    val size: Int,
    optimization: MenuOptimization,
    parent: Menu<*>?
) : Menu<T>(title, optimization, parent) {

    val items: HashMap<Int, MenuItem<T>> = hashMapOf()
    fun firstEmptySlot(): Int {
        for (i in 0..<size)
            if (items[i] == null) return i
        return -1
    }

    fun setItem(slot: Int, item: MenuItem<T>) {
        items[slot] = item
        getWrapper<MenuWrapper>()?.setItem(slot, item.itemStack)
    }
    fun setItem(slot: Int, item: ItemStack, action: ClickData<T>.() -> Unit = {}) = setItem(slot, MenuItem(item, action))
    fun setItem(slot: Int, material: Material, action: ClickData<T>.() -> Unit = {}) = setItem(slot, MenuItem(ItemStack(material), action))

    @Suppress("UNCHECKED_CAST")
    fun setItem(slot: Int, item: ItemStack, action: ClickAction) = setItem(slot, MenuItem(item).apply { addAction(action) } as MenuItem<T>)

    fun addItem(item: MenuItem<T>) {
        if (size == items.size) return
        setItem(firstEmptySlot(), item)
    }
    fun addItem(item: ItemStack) = addItem(MenuItem.fromItem(item))
    fun addItem(material: Material) = addItem(MenuItem.fromItem(ItemStack(material)))

    // Set multiple items methods
    fun setItems(item: MenuItem<T>, slots: List<Int>) {
        for (slot in slots) setItem(slot, item)
    }
    fun setItems(item: ItemStack, slots: List<Int>) = setItems(MenuItem(item), slots)
    fun setItems(material: Material, slots: List<Int>) = setItems(MenuItem(ItemStack(material)), slots)

    fun setItems(item: MenuItem<T>, vararg slots: Int) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, vararg slots: Int) = setItems(item, slots.toList())
    fun setItems(material: Material, vararg slots: Int) = setItems(ItemStack(material), slots.toList())

    fun setItems(item: MenuItem<T>, slots: IntRange) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, slots: IntRange) = setItems(item, slots.toList())
    fun setItems(material: Material, slots: IntRange) = setItems(ItemStack(material), slots.toList())

    fun setItems(item: MenuItem<T>, action: ClickAction, slots: List<Int>) {
        for (slot in slots) setItem(slot, item.apply { addAction(action) })
    }
    fun setItems(item: ItemStack, action: ClickAction, slots: List<Int>) = setItems(MenuItem(item), action, slots)
    fun setItems(material: Material, action: ClickAction, slots: List<Int>) = setItems(MenuItem(ItemStack(material)), action, slots)

    fun setItems(item: MenuItem<T>, action: ClickAction, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(material: Material, action: ClickAction, vararg slots: Int) = setItems(ItemStack(material), action, slots.toList())

    fun setItems(item: MenuItem<T>, action: ClickAction, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(material: Material, action: ClickAction, slots: IntRange) = setItems(ItemStack(material), action, slots.toList())

    fun setItems(item: MenuItem<T>, action: ClickData<T>.() -> Unit, slots: List<Int>) {
        for (slot in slots) setItem(slot, item.apply { addAction(action) })
    }
    fun setItems(item: ItemStack, action: ClickData<T>.() -> Unit, slots: List<Int>) = setItems(MenuItem(item), action, slots)
    fun setItems(material: Material, action: ClickData<T>.() -> Unit, slots: List<Int>) = setItems(MenuItem(ItemStack(material)), action, slots)

    fun setItems(item: MenuItem<T>, action: ClickData<T>.() -> Unit, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickData<T>.() -> Unit, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(material: Material, action: ClickData<T>.() -> Unit, vararg slots: Int) = setItems(ItemStack(material), action, slots.toList())

    fun setItems(item: MenuItem<T>, action: ClickData<T>.() -> Unit, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickData<T>.() -> Unit, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(material: Material, action: ClickData<T>.() -> Unit, slots: IntRange) = setItems(ItemStack(material), action, slots.toList())

    fun isSlotEmpty(slot: Int) = !items.containsKey(slot)
    fun clear() = items.clear()

    @ApiStatus.OverrideOnly
    open fun onClick(data: ClickData<T>) {
        // should be overridden
    }

    @Suppress("UNCHECKED_CAST")
    abstract class Builder<T, R : ContainerMenu<R>>(
        title: Component,
        val size: Int,
        optimization: MenuOptimization,
        parent: Menu<*>?
    ) : Menu.Builder<T, R>(title, optimization, parent) {

        protected var items: HashMap<Int, MenuItem<R>> = hashMapOf()
        protected var clickActions: MutableList<ClickData<R>.() -> (Unit)> = mutableListOf()

        fun onClick(data: ClickData<R>.() -> (Unit)): T = apply { clickActions.add(data) } as T

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
    }
}