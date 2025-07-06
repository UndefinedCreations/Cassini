package com.undefined.cassini.data.dialog

import com.google.gson.JsonObject

/**
 * Represents an action to perform inside a dialog.
 */
abstract class DialogAction(val type: String) {
    open fun toJson(): JsonObject = JsonObject().also { json ->
        json.addProperty("type", type)
    }
}