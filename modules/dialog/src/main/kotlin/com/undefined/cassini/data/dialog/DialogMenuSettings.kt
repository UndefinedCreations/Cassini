package com.undefined.cassini.data.dialog

import com.undefined.cassini.menu.MenuSettings
import com.undefined.cassini.menu.dialog.DialogMenu
import net.kyori.adventure.text.Component

/**
 * Contains information about a [DialogMenu].
 *
 * @param externalTitle Name to be used for a button leading to this dialog.
 * @param canCloseWithEscape If the dialog be dismissed with the escape key. Defaults to true.
 * @param afterAction An additional operation performed on the dialog after click or submit actions. Defaults to `AfterAction.CLOSE`.
 */
class DialogMenuSettings(
    var externalTitle: Component,
    var canCloseWithEscape: Boolean = true,
    var afterAction: AfterAction = AfterAction.CLOSE,
) : MenuSettings()