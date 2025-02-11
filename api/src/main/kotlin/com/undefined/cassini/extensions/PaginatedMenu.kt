package com.undefined.cassini.extensions

import com.undefined.cassini.ContainerMenu
import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.data.item.PageItem
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.cassini.util.openBuilderMenu
import com.undefined.cassini.util.update
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

abstract class PaginatedMenu(
    title: Component,
    size: Int,
    val list: (Player) -> List<MenuItem<ChestMenu>>,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu<*>? = null
) : ChestMenu(title, size, optimization, parent) {

    abstract val backButton: PageItem
    abstract val nextButton: PageItem

    var pageItems: List<MenuItem<ChestMenu>> = listOf()
        private set
    var currentPage: Int = 1
        private set
    var totalPages: Int = 1
        private set

    fun nextPage(player: Player, update: Boolean = true): Boolean {
        if (currentPage >= totalPages) return false
        currentPage++
        if (update) update(player)
        return true
    }

    fun backPage(player: Player, update: Boolean = true): Boolean {
        if (currentPage <= 1) return false
        currentPage--
        if (update) update(player)
        return true
    }

    fun isOnLastPage(): Boolean = currentPage == totalPages
    fun isOnFirstPage(): Boolean = currentPage == 1

    override fun afterinitialize(player: Player) {
        updateList(player)

        val items: HashMap<Int, MenuItem<ChestMenu>> = items
        val emptySlots: MutableList<Int> = mutableListOf()
        for (slot in 0..size)
            if (slot !in items.keys) emptySlots.add(slot)
        if (!isOnFirstPage()) emptySlots.add(nextButton.slot)
        if (!isOnLastPage()) emptySlots.add(backButton.slot)
        totalPages = pageItems.size / emptySlots.size
        if (!isOnFirstPage()) items[backButton.slot] = MenuItem(backButton.item, { backPage(player) })
        if (!isOnLastPage()) items[nextButton.slot] = MenuItem(nextButton.item, { nextPage(player) })

        val firstIndex = (currentPage - 1) * emptySlots.lastIndex
        val lastIndex = (currentPage) * emptySlots.lastIndex
        val currentPageItems: MutableList<MenuItem<ChestMenu>> = if (firstIndex <= pageItems.size) pageItems.subList(
            firstIndex,
            if (lastIndex <= pageItems.size) lastIndex else pageItems.lastIndex
        ).toMutableList() else mutableListOf()
        for (slot in 0..<size)
            if (slot !in items.keys) {
                items[slot] = currentPageItems.first()
                currentPageItems.removeFirst()
            }
    }

    fun update(player: Player) {
        updateList(player)
        player.update(this)
        player.openMenu(this, true)
    }

    fun updateList(player: Player) {
        pageItems = list(player)
    }

}