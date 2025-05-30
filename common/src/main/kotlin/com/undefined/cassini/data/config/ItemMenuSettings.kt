package com.undefined.cassini.data.config

import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.menu.item.ItemMenu

/**
 * Contains information about any [ItemMenu].
 *
 * @param reopenInventory Whether to close and open a new inventory, or just modify the contents.
 */
class ItemMenuSettings(
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    val reopenInventory: Boolean = false,
) : MenuSettings(optimization)