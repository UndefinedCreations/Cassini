package com.undefined.cassini.menu.dialog

import com.google.gson.JsonArray
import com.undefined.cassini.data.dialog.DialogButton
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component

/**
 * A dialog screen with a scrollable list of buttons arranged in columns.
 *
 * If [exitAction] is present, a button for it will appear in the footer, otherwise the footer is not present. [exitAction] is also used for the Escape action.
 */
open class MultiActionDialogMenu(
    title: Component,
    val buttons: List<DialogButton>,
    val columns: Int = DEFAULT_COLUMNS,
    val exitAction: DialogButton? = null,
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : DialogMenu("minecraft:multi_action", title, parent, settings) {

    override val totalButtons: List<DialogButton>
        get() = if (exitAction != null) super.totalButtons + buttons + exitAction else super.totalButtons + buttons

    override fun toJson() = super.toJson().also { json ->
        val jsonButtons = JsonArray().also { array ->
            for (button in buttons) array.add(button.toJson())
        }
        json.add("actions", jsonButtons)
        json.addProperty("columns", columns)
        if (exitAction != null) json.add("exit_columns", exitAction.toJson())
    }

    companion object {
        const val DEFAULT_COLUMNS: Int = 2
    }

}