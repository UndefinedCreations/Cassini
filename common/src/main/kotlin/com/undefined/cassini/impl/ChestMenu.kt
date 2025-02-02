package com.undefined.cassini.impl

import com.undefined.cassini.Menu
import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.data.pattern.MenuPattern
import com.undefined.cassini.exception.InvalidSlotException
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class ChestMenu(
    title: Component,
    size: Int,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu? = null
) : Menu(title, size, optimization, parent) {

    constructor(
        title: String,
        size: Int,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu? = null
    ) : this(Component.text(title), size, optimization, parent)

    fun createInventory(init: ChestMenu.() -> Unit) { this.init() }

    fun applyPattern(pattern: MenuPattern<ChestMenu>) = pattern.apply(this)

    fun setRow(item: GUIItem, row: Int) =
        ((ROW_SIZE * (row - 1)) until (ROW_SIZE * (row - 1) + ROW_SIZE)).run {
            if (row in 1..9 && (row * ROW_SIZE) < size) setItems(item, this) else throw InvalidSlotException(this)
        }

    fun setRow(item: ItemStack, row: Int) = setRow(GUIItem(item), row)

    fun setRows(item: GUIItem, rows: List<Int>) {
        for (row in rows) setRow(item, row)
    }

    fun setRows(item: GUIItem, vararg rows: Int) = setRows(item, rows.toList())

    fun setRows(item: GUIItem, rows: IntRange) = setRows(item, rows.toList())

    fun setRows(item: ItemStack, rows: List<Int>) = setRows(GUIItem(item), rows)

    fun setRows(item: ItemStack, vararg rows: Int) = setRows(item, rows.toList())

    fun setRows(item: ItemStack, rows: IntRange) = setRows(item, rows.toList())

    fun setColumn(item: GUIItem, column: Int) =
        if (column in 1..9)
            generateSequence(column - 1) { it + COLUMN_SIZE }
                .takeWhile { it < size }
                .toList().run { setItems(item, this) }
        else
            throw InvalidSlotException(column * 9)

    fun setColumn(item: ItemStack, column: Int) = setColumn(GUIItem(item), column)

    fun setColumns(item: GUIItem, columns: List<Int>) {
        for (column in columns) setColumn(item, column)
    }

    companion object {
        private const val ROW_SIZE = 9
        private const val COLUMN_SIZE = 9
    }

    class Builder(
        title: Component,
        size: Int,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu? = null
    ): Menu.Builder<Builder, ChestMenu>(title, size, optimization, parent) {

        constructor(
            title: String,
            size: Int,
            optimization: MenuOptimization = MenuOptimization.NORMAL,
            parent: Menu? = null
        ) : this(Component.text(title), size, optimization, parent)

        private val rows: HashMap<Int, GUIItem> = hashMapOf()
        private val columns: HashMap<Int, GUIItem> = hashMapOf()

        private val patternList: MutableList<MenuPattern<ChestMenu>> = mutableListOf()

        fun applyPattern(pattern: MenuPattern<ChestMenu>): Builder = pattern.apply { patternList.add(pattern) }.let { this }

        fun setRow(guiItem: GUIItem, row: Int): Builder = apply {
            rows[row] = guiItem
        }

        fun setRow(item: ItemStack, row: Int): Builder = setRow(GUIItem(item), row)

        fun setRows(item: GUIItem, rows: List<Int>): Builder = apply {
            for (row in rows) setRow(item, row)
        }

        fun setRows(item: GUIItem, vararg rows: Int): Builder = setRows(item, rows.toList())

        fun setRows(item: GUIItem, rows: IntRange): Builder = setRows(item, rows.toList())

        fun setRows(item: ItemStack, rows: List<Int>): Builder = setRows(GUIItem(item), rows)

        fun setRows(item: ItemStack, vararg rows: Int): Builder = setRows(item, rows.toList())

        fun setRows(item: ItemStack, rows: IntRange): Builder = setRows(item, rows.toList())

        fun setColumn(guiItem: GUIItem, column: Int): Builder {
            columns[column] = guiItem
            return this
        }

        fun setColumn(item: ItemStack, column: Int): Builder = setColumn(GUIItem(item), column)

        fun setColumns(item: GUIItem, columns: List<Int>): Builder = apply {
            for (column in columns) setColumn(item, column)
        }

        override fun build(): ChestMenu =
            object : ChestMenu(title, size, optimization, parent) {
                override fun initialize(player: Player) {
                    initializeList.forEach { it(player) }
                }
                override fun onClick(context: CassiniContext) {
                    onClick.forEach { it(context) }
                }
                override fun onOpen(player: Player) {
                    onOpen.forEach { it(player) }
                }
                override fun onClose(player: Player) {
                    onClose.forEach { it(player) }
                }
            }.also { chestMenu ->
                initMenu(chestMenu)
                rows.forEach { chestMenu.setRow(it.value, it.key) }
                columns.forEach { chestMenu.setColumn(it.value, it.key) }
            }
    }
}