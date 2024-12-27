package com.undefined.cassini.impl

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.data.item.PageItem
import com.undefined.cassini.util.openMenu
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

abstract class PaginatedMenu<T>(
    title: Component,
    size: Int,
    val list: (Player) -> List<T>,
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu? = null
) : ChestMenu(title, size, optimization, parent) {

    abstract val backButton: PageItem
    abstract val nextButton: PageItem

    open fun onPageItemClick(slot: Int, item: GUIItem) {}
    val currentPage: Int = 1
    val totalPages: Int = 1

    fun nextPage(player: Player) {
        currentPage
        player.openMenu()
    }

    fun backPage(player: Player) {

    }

}