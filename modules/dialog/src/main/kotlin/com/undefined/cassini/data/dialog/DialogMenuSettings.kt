package com.undefined.cassini.data.dialog

import com.undefined.cassini.menu.MenuSettings
import com.undefined.cassini.menu.dialog.DialogMenu
import net.kyori.adventure.text.Component

/**
 * Contains information about a [DialogMenu].
 *
 * @param externalTitle Name to be used for a button leading to this dialog.
 * @param canCloseWithEscape If the dialog be dismissed with Escape key. Defaults to true.
 */
class DialogMenuSettings(
    val externalTitle: Component,
    val canCloseWithEscape: Boolean = true,
) : MenuSettings()