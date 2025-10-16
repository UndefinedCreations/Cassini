package com.undefined.cassini.element.dialog.input

import com.google.gson.JsonArray
import com.undefined.cassini.data.dialog.DialogListOption
import net.kyori.adventure.text.Component

/**
 * A [DialogInputElement] that represents a list.
 *
 * @param key A string identifier of value used when submitting data, must be a valid template argument (letters, digits and _).
 * @param label The list label.
 * @param labelVisible Whether the label is visible. Defaults to `true`.
 * @param width The width of the input. A value between `1` and `1024`, defaults to `200`.
 * @param options A non-empty list of [DialogListOption].
 */
class ListDialogInput(
    key: String,
    label: Component,
    val options: MutableList<DialogListOption> = mutableListOf(),
    val labelVisible: Boolean = true,
    val width: Int = 200,
) : DialogInputElement("minecraft:single_option", key, label) {

    override fun toJson() = super.toJson().also { json ->
        if (options.isEmpty()) throw IllegalArgumentException("List dialog options cannot ")
        json.addProperty("label_visible", labelVisible)
        json.addProperty("width", width)
        json.add("options", JsonArray().also { optionsJson ->
            for (option in options) optionsJson.add(option.toJson())
        })
    }

}