package com.undefined.cassini.container.dialog

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.undefined.cassini.container.Container
import com.undefined.cassini.element.dialog.body.DialogBodyElement
import com.undefined.cassini.menu.dialog.DialogMenu

/**
 * Represents a container in a [DialogMenu].
 */
class DialogBodyContainer() : Container<DialogBodyContainer, DialogBodyElement>() {
    fun toJson(): JsonElement = JsonArray().also { json ->
        for (element in elements) json.add(element.toJson())
    }
}