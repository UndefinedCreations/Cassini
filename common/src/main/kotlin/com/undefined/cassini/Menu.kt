package com.undefined.cassini

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickAction
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.impl.ChestMenu
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

abstract class Menu(var title: Component, val size: Int, val optimization: MenuOptimization, val parent: Menu?) {

    private var hasBeenInitialized = false
    open val items: HashMap<Int, GUIItem> = hashMapOf()
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
        items[slot] = GUIItem(item)
    }

    fun setItem(slot: Int, item: GUIItem) {
        items[slot] = item
    }

    fun addItem(item: ItemStack) {
        if (size == items.size) return
        items[firstEmptySlot()] = GUIItem(item)
    }

    fun addItem(item: GUIItem) {
        if (size == items.size) return
        items[firstEmptySlot()] = item
    }

    // Set multiple items methods
    fun setItems(item: GUIItem, slots: List<Int>) {
        for (slot in slots) setItem(slot, item)
    }
    fun setItems(item: GUIItem, vararg slots: Int) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, vararg slots: Int) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, slots: List<Int>) = setItems(GUIItem(item), slots)
    fun setItems(item: GUIItem, slots: IntRange) = setItems(item, slots.toList())
    fun setItems(item: ItemStack, slots: IntRange) = setItems(item, slots.toList())

    fun setItems(item: GUIItem, action: ClickAction, slots: List<Int>) {
        for (slot in slots) setItem(slot, item.apply { addAction(action) })
    }
    fun setItems(item: GUIItem, action: ClickAction, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, slots: List<Int>) = setItems(GUIItem(item), action, slots)
    fun setItems(item: GUIItem, action: ClickAction, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: ClickAction, slots: IntRange) = setItems(item, action, slots.toList())

    fun setItems(item: GUIItem, action: CassiniContext.() -> Unit, slots: List<Int>) {
        for (slot in slots) setItem(slot, item.apply { addAction(action) })
    }
    fun setItems(item: GUIItem, action: CassiniContext.() -> Unit, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: CassiniContext.() -> Unit, vararg slots: Int) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: CassiniContext.() -> Unit, slots: List<Int>) = setItems(GUIItem(item), action, slots)
    fun setItems(item: GUIItem, action: CassiniContext.() -> Unit, slots: IntRange) = setItems(item, action, slots.toList())
    fun setItems(item: ItemStack, action: CassiniContext.() -> Unit, slots: IntRange) = setItems(item, action, slots.toList())

    fun isSlotEmpty(slot: Int) = !items.containsKey(slot)
    fun clear() = items.clear()

    @ApiStatus.OverrideOnly
    open fun onClick(context: CassiniContext) {}

    @ApiStatus.OverrideOnly
    open fun onOpen(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun onClose(player: Player) {}

    @Suppress("UNCHECKED_CAST")
    abstract class Builder<T, R: Menu>(
        var title: Component,
        val size: Int,
        val optimization: MenuOptimization,
        val parent: Menu?
    ) {

        private var itemsList: HashMap<Int, GUIItem> = hashMapOf()
        private var addItems: MutableList<GUIItem> = mutableListOf()

        protected var onClick: MutableList<CassiniContext.() -> (Unit)> = mutableListOf()
        protected var onOpen: MutableList<Player.() -> (Unit)> = mutableListOf()
        protected var onClose: MutableList<Player.() -> (Unit)> = mutableListOf()
        protected var initializeList: MutableList<Player.() -> (Unit)> = mutableListOf()

        fun onClick(context: CassiniContext.() -> (Unit)): T = apply { onClick.add(context) } as T
        fun onOpen(open: Player.() -> (Unit)): T = apply { onOpen.add(open) } as T
        fun onClose(close: Player.() -> (Unit)): T = apply { onClose.add(close) } as T
        fun initialize(initialize: Player.() -> (Unit)): T = apply { initializeList.add(initialize) } as T

        fun setItem(slot: Int, item: GUIItem): T {
            itemsList[slot] = item
            return this as T
        }
        fun setItem(slot: Int, item: ItemStack): T = setItem(slot, GUIItem.fromItem(item))
        fun setItems(item: GUIItem, slots: List<Int>): T {
            for (slot in slots) setItem(slot, item)
            return this as T
        }
        fun setItems(item: GUIItem, vararg slots: Int): T = setItems(item, slots.toList())
        fun setItems(item: ItemStack, vararg slots: Int): T = setItems(item, slots.toList())
        fun setItems(item: ItemStack, slots: List<Int>): T = setItems(GUIItem(item), slots)
        fun setItems(item: GUIItem, slots: IntRange): T = setItems(item, slots.toList())
        fun setItems(item: ItemStack, slots: IntRange): T = setItems(item, slots.toList())

        fun setItems(item: GUIItem, action: ClickAction, slots: List<Int>): T {
            for (slot in slots) setItem(slot, item.apply { addAction(action) })
            return this as T
        }
        fun setItems(item: GUIItem, action: ClickAction, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickAction, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickAction, slots: List<Int>): T = setItems(GUIItem(item), action, slots)
        fun setItems(item: GUIItem, action: ClickAction, slots: IntRange): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: ClickAction, slots: IntRange): T = setItems(item, action, slots.toList())

        fun setItems(item: GUIItem, action: CassiniContext.() -> Unit, slots: List<Int>): T {
            for (slot in slots) setItem(slot, item.apply { addAction(action) })
            return this as T
        }
        fun setItems(item: GUIItem, action: CassiniContext.() -> Unit, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: CassiniContext.() -> Unit, vararg slots: Int): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: CassiniContext.() -> Unit, slots: List<Int>): T = setItems(GUIItem(item), action, slots)
        fun setItems(item: GUIItem, action: CassiniContext.() -> Unit, slots: IntRange): T = setItems(item, action, slots.toList())
        fun setItems(item: ItemStack, action: CassiniContext.() -> Unit, slots: IntRange): T = setItems(item, action, slots.toList())

        fun addItem(item: ItemStack): T = addItem(GUIItem.fromItem(item))
        fun addItem(item: GUIItem): T = apply { addItems.add(item) } as T

        protected fun initMenu(menu: Menu) {
            itemsList.forEach { menu.setItem(it.key, it.value) }
            addItems.forEach { menu.addItem(it) }
        }
        abstract fun build(): R
    }
}