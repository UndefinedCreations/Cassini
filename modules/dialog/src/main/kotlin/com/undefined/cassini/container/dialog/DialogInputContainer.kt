package com.undefined.cassini.container.dialog

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.undefined.cassini.container.Container
import com.undefined.cassini.element.dialog.body.DialogBodyElement
import com.undefined.cassini.element.dialog.input.DialogInputElement
import com.undefined.cassini.menu.dialog.DialogMenu

/**
 * Represents a container containing any number [DialogInputElement]s in a [DialogMenu].
 */
class DialogInputContainer() : Container<DialogInputContainer, DialogInputElement>() {
    fun toJson(): JsonElement = JsonArray().also { json ->
        println("input elements: ${elements}")
        for (element in elements) json.add(element.toJson())
        println("inputs: $json")
    }
}