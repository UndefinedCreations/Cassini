package com.undefined.cassini.menu.dialog

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component
import kotlin.system.exitProcess

/**
 * A dialog screen with scrollable list of buttons leading to other dialogs. Titles of those buttons will be taken from their respective [DialogMenuSettings.externalTitle].
 *
 * @param title The dialog title displayed in the header.
 * @param dialogs The list of dialogs. Can either be a [String] ID, [JsonObject] representing a dialog, or a [JsonArray] containing either IDs or dialog [JsonObject]s.
 * @param exitButton The exit button displayed in the footer. If the [exitButton] is not present, the footer will not appear.
 * @param columns Positive integer describing the number of columns. Defaults to `2`.
 * @param buttonWidth The width of the button. A value between `1` and `1024`, defaults to `150`.
 * @param parent The menu this was opened from, if any. This menu might use it in some cases to go back to the previously opened menu.
 */
open class DialogListDialogMenu(
    title: Component,
    val dialogs: JsonElement,
    val exitButton: DialogButton? = null,
    val columns: Int = DEFAULT_COLUMNS,
    val buttonWidth: Int = DEFAULT_BUTTON_WIDTH,
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : DialogMenu("minecraft:dialog_list", title, parent, settings) {

    /**
     * A dialog screen with scrollable list of buttons leading to other dialogs. Titles of those buttons will be taken from their respective [DialogMenuSettings.externalTitle].
     *
     * @param title The dialog title displayed in the header.
     * @param dialogs A list containing [DialogMenu]s.
     * @param exitButton The exit button displayed in the footer. If the [exitButton] is not present, the footer will not appear.
     * @param columns Positive integer describing the number of columns. Defaults to `2`.
     * @param buttonWidth The width of the button. A value between `1` and `1024`, defaults to `150`.
     * @param parent The menu this was opened from, if any. This menu might use it in some cases to go back to the previously opened menu.
     */
    constructor(
        title: Component,
        dialogs: Collection<DialogMenu>,
        exitButton: DialogButton? = null,
        columns: Int = DEFAULT_COLUMNS,
        buttonWidth: Int = DEFAULT_BUTTON_WIDTH,
        parent: CassiniMenu<*, *>? = null,
        settings: DialogMenuSettings = DialogMenuSettings(title),
    ) : this(title, JsonArray().also { for (dialog in dialogs) it.add(dialog.toJson()) }, exitButton, columns, buttonWidth, parent, settings)

    override val totalButtons: List<DialogButton>
        get() = if (exitButton != null) super.totalButtons + exitButton else super.totalButtons

    override fun toJson() = super.toJson().also { json ->
        json.add("dialogs", dialogs)
        if (exitButton != null) json.add("exit_action", exitButton.toJson())
        json.addProperty("columns", columns)
        json.addProperty("button_width", buttonWidth)
    }

    companion object {
        const val DEFAULT_COLUMNS = 2
        const val DEFAULT_BUTTON_WIDTH = 200
    }

}