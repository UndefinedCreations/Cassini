package com.undefined.cassini.container.dialog

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.undefined.cassini.container.SimpleContainerImpl
import com.undefined.cassini.element.dialog.input.DialogInputElement
import com.undefined.cassini.menu.dialog.DialogMenu

/**
 * Represents a container containing any number [DialogInputElement]s in a [DialogMenu].
 */
class DialogInputContainer(menu: DialogMenu) : AbstractDialogContainer<DialogInputContainer, DialogInputElement>(menu) {
    override fun toJson(): JsonElement = JsonArray().also { json ->
        for (element in getAllElements()) json.add(element.toJson())
    }
}