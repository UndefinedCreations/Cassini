package com.undefined.cassini.menu

import com.undefined.cassini.menu.item.ItemMenu

/**
 * Contains general information on a specific "general" menu.
 * A "general menu" refers to a menu that is extended by other menus and is by itself not a menu, such as [ItemMenu] or [CassiniMenu].
 *
 * @param optimization A [MenuOptimization] instance dictating how the menu should be optimized.
 */
abstract class MenuSettings(val optimization: MenuOptimization)