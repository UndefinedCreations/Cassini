package com.undefined.cassini.impl

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.exception.InvalidSlotException
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

abstract class ChestMenu(title: Component, size: Int, optimization: MenuOptimization = MenuOptimization.NORMAL, parent: Menu? = null) : Menu(title, size, optimization, parent) {

    constructor(title: String, size: Int, optimization: MenuOptimization = MenuOptimization.NORMAL, parent: Menu? = null): this(Component.text(title), size, optimization, parent)



    fun createInventory(init: ChestMenu.() -> Unit) { init(this) }

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
}