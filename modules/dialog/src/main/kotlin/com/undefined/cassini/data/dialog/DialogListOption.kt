package com.undefined.cassini.data.dialog

import com.google.gson.JsonObject
import com.undefined.cassini.element.dialog.input.ListDialogInput
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

/**
 * Represents an option within a [ListDialogInput].
 *
 * @param id The value to be sent on submit.
 * @param display The text component to be displayed.
 * @param initial If `true`, the option chosen will be the initial one. There can only be one option set to `true`. Defaults to `false`.
 */
data class DialogListOption(val id: String, val display: Component, val initial: Boolean = false) {
    fun toJson(): JsonObject = JsonObject().also { json ->
        json.addProperty("id", id)
        json.add("display", GsonComponentSerializer.gson().serializeToTree(display))
        json.addProperty("initial", initial)
    }
}