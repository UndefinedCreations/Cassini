package com.undefined.cassini.element.dialog.input

import net.kyori.adventure.text.Component

/**
 * A [DialogInputElement] that enables users to enter text input within a dialog.
 *
 * @param key A string identifier of value used when submitting data, must be a valid template argument (letters, digits and _).
 * @param label The label to be displayed to the left or right.
 * @param width The width of the input. A value between `1` and `1024`, and defaults to `200`.
 * @param labelVisible Controls if the label is visible. Default to `true`.
 * @param initial The initial value of the text input.
 * @param maxLength Maximum length of the input. Defaults to `32`.
 * @param multiline If present, allows players to input multiple lines.
 */
class TextDialogInput(
    key: String,
    label: Component,
    val width: Int = DEFAULT_WIDTH,
    val labelVisible: Boolean = true,
    val initial: String = "",
    val maxLength: Int = DEFAULT_MAX_LENGTH,
    val multiline: MultiLineOptions? = null,
) : DialogInputElement("minecraft:text", key, label) {

    override fun toJson() = super.toJson().also { json ->
        json.addProperty("width", width)
        json.addProperty("label_visible", labelVisible)
        if (initial.isNotBlank()) json.addProperty("initial", initial)
        json.addProperty("max_length", maxLength)
        if (multiline != null) json.add("multiline", multiline.toJson())
    }

    companion object {
        const val DEFAULT_WIDTH = 200
        const val DEFAULT_MAX_LENGTH = 200
    }

}