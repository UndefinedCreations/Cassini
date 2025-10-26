package com.undefined.cassini.menu.dialog

import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.menu.Menu
import net.kyori.adventure.text.Component

/**
 * A dialog screen with scrollable list of server links.
 *
 * @param title The dialog title displayed in the header.
 * @param exitButton The exit button displayed in the footer. If the [exitButton] is not present, the footer will not appear.
 * @param columns Positive integer describing the number of columns. Defaults to `2`.
 * @param buttonWidth The width of the button. A value between `1` and `1024`, defaults to `150`.
 * @param parent The menu this was opened from, if any. This menu might use it in some cases to go back to the previously opened menu.
 */
open class ServerLinksDialogMenu(
    title: Component,
    val exitButton: DialogButton? = null,
    val columns: Int = DEFAULT_COLUMNS,
    val buttonWidth: Int = DEFAULT_BUTTON_WIDTH,
    parent: Menu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : DialogMenu("minecraft:server_links", title, parent, settings) {

    override val totalButtons: List<DialogButton>
        get() = if (exitButton != null) super.totalButtons + exitButton else super.totalButtons

    override fun toJson() = super.toJson().also { json ->
        if (exitButton != null) json.add("exit_action", exitButton.toJson())
        json.addProperty("columns", columns)
        json.addProperty("button_width", buttonWidth)
    }

    companion object {
        const val DEFAULT_COLUMNS = 2
        const val DEFAULT_BUTTON_WIDTH = 2
    }

}