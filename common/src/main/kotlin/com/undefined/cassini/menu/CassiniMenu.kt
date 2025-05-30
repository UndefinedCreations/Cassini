package com.undefined.cassini.menu

import com.undefined.cassini.data.config.MenuSettings
import net.kyori.adventure.text.Component

/**
 * This is the base for any menu, although it's no menu in itself.
 *
 * @param title The menu title.
 * @param settings The menu settings. Contains information on the menu's functionality.
 * @param parent The menu this was opened from, if any. This menu might use it in some cases to go back to the previously opened menu.
 *
 * @param T The extending class itself.
 * @param C The [MenuSettings] type.
 */
abstract class CassiniMenu<T : CassiniMenu<T, *>, C : MenuSettings>(
    val title: Component,
    val parent: CassiniMenu<*, *>?,
) {
    abstract val settings: C
}