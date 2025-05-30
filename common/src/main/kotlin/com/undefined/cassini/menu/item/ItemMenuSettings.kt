package com.undefined.cassini.menu.item

import com.undefined.cassini.menu.MenuOptimization
import com.undefined.cassini.menu.MenuSettings

/**
 * Contains information about any [ItemMenu].
 *
 * @param reopenInventory Whether to close and open a new inventory, or just modify the contents.
 */
class ItemMenuSettings(
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    val reopenInventory: Boolean = false,
) : MenuSettings(optimization)