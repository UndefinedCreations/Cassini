package com.undefined.cassini.impl

import com.undefined.cassini.Cassini
import com.undefined.cassini.ContainerMenu
import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.data.pattern.MenuPattern
import com.undefined.cassini.exception.InvalidSlotException
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class ChestMenu(
    title: Component,
    size: Int,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu<*>? = null
) : ContainerMenu<ChestMenu>(title, size, optimization, parent) {

    constructor(
        title: String,
        size: Int,
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ) : this(Cassini.miniMessage.deserialize(title), size, optimization, parent)

    fun applyPattern(pattern: MenuPattern<ChestMenu>) = pattern.apply(this)

    fun setRow(item: MenuItem<ChestMenu>, row: Int) =
        ((ROW_SIZE * (row - 1)) until (ROW_SIZE * (row - 1) + ROW_SIZE)).run {
            if (row in 1..9 && (row * ROW_SIZE) < size) setItems(item, this) else throw InvalidSlotException(this)
        }
    fun setRow(item: ItemStack, row: Int) = setRow(MenuItem(item), row)
    fun setRows(item: MenuItem<ChestMenu>, rows: List<Int>) {
        for (row in rows) setRow(item, row)
    }
    fun setRows(item: MenuItem<ChestMenu>, vararg rows: Int) = setRows(item, rows.toList())
    fun setRows(item: MenuItem<ChestMenu>, rows: IntRange) = setRows(item, rows.toList())
    fun setRows(item: ItemStack, rows: List<Int>) = setRows(MenuItem(item), rows)
    fun setRows(item: ItemStack, vararg rows: Int) = setRows(item, rows.toList())
    fun setRows(item: ItemStack, rows: IntRange) = setRows(item, rows.toList())

    fun setColumn(item: MenuItem<ChestMenu>, column: Int) =
        if (column in 1..9)
            generateSequence(column - 1) { it + COLUMN_SIZE }
                .takeWhile { it < size }
                .toList().run { setItems(item, this) }
        else
            throw InvalidSlotException(column * 9)

    fun setColumn(item: ItemStack, column: Int) = setColumn(MenuItem(item), column)
    fun setColumns(item: MenuItem<ChestMenu>, columns: List<Int>) {
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
        parent: Menu<*>? = null
    ): ContainerMenu.Builder<Builder, ChestMenu>(title, size, optimization, parent) {

        constructor(
            title: String,
            size: Int,
            optimization: MenuOptimization = MenuOptimization.NORMAL,
            parent: Menu<*>? = null
        ) : this(Cassini.miniMessage.deserialize(title), size, optimization, parent)

        private val patternList: MutableList<MenuPattern<ChestMenu>> = mutableListOf()

        fun applyPattern(pattern: MenuPattern<ChestMenu>): Builder = pattern.apply { patternList.add(pattern) }.let { this }

        fun setRow(item: MenuItem<ChestMenu>, row: Int) =
            ((ROW_SIZE * (row - 1)) until (ROW_SIZE * (row - 1) + ROW_SIZE)).run {
                if (row in 1..9 && (row * ROW_SIZE) <= size) setItems(item, this) else throw InvalidSlotException(this)
            }
        fun setRow(item: ItemStack, row: Int) = setRow(MenuItem(item), row)
        fun setRows(item: MenuItem<ChestMenu>, rows: List<Int>) {
            for (row in rows) setRow(item, row)
        }
        fun setRows(item: MenuItem<ChestMenu>, vararg rows: Int) = setRows(item, rows.toList())
        fun setRows(item: MenuItem<ChestMenu>, rows: IntRange) = setRows(item, rows.toList())
        fun setRows(item: ItemStack, rows: List<Int>) = setRows(MenuItem(item), rows)
        fun setRows(item: ItemStack, vararg rows: Int) = setRows(item, rows.toList())
        fun setRows(item: ItemStack, rows: IntRange) = setRows(item, rows.toList())

        fun setColumn(item: MenuItem<ChestMenu>, column: Int) =
            if (column in 1..9)
                generateSequence(column - 1) { it + COLUMN_SIZE }
                    .takeWhile { it < size }
                    .toList().run { setItems(item, this) }
            else
                throw InvalidSlotException(column * 9)

        fun setColumn(item: ItemStack, column: Int) = setColumn(MenuItem(item), column)
        fun setColumns(item: MenuItem<ChestMenu>, columns: List<Int>) {
            for (column in columns) setColumn(item, column)
        }

        override fun build(): ChestMenu =
            object : ChestMenu(title, size, optimization, parent) {
                override fun initialize(player: Player) {
                    for (initialization in initializations) initialization(player)
                    initMenu(this)
                }
                override fun onClick(data: ClickData<ChestMenu>) {
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