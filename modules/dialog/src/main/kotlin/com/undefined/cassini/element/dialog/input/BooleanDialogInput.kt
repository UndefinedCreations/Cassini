package com.undefined.cassini.element.dialog.input

import net.kyori.adventure.text.Component

/**
 * A [DialogInputElement] that represents a checkbox in a dialog.
 *
 * @param key A string identifier of value used when submitting data, must be a valid template argument (letters, digits and _).
 * @param label The label to be displayed to the right.
 * @param initial The initial value of the checkbox. Defaults to `false`.
 * @param onTrue String to send when the value is `true`. Defaults to "`true`".
 * @param onFalse String to send when the value is `false`. Defaults to "`false`".
 */
class BooleanDialogInput(
    key: String,
    label: Component,
    val initial: Boolean = false,
    val onTrue: String = "true",
    val onFalse: String = "false",
) : DialogInputElement("minecraft:boolean", key, label) {

    override fun toJson() = super.toJson().also { json ->
        json.addProperty("initial", initial)
        json.addProperty("on_true", onTrue)
        json.addProperty("on_false", onFalse)
    }

}