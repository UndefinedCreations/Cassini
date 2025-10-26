package com.undefined.cassini.element.dialog

import com.google.gson.JsonObject
import com.undefined.cassini.menu.dialog.DialogMenu

/**
 * Represents either a body or input element in a [DialogMenu].
 *
 * @param type The element type (e.g. `minecraft:plain_message`, `minecraft:text`).
 */
abstract class DialogElement(val type: String) : AbstractDialogElement() {
    override fun toJson(): JsonObject = JsonObject().also { json ->
        json.addProperty("type", type)
    }
}