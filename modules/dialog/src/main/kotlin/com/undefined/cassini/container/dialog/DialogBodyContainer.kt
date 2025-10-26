package com.undefined.cassini.container.dialog

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.undefined.cassini.element.dialog.body.DialogBodyElement
import com.undefined.cassini.menu.dialog.DialogMenu

/**
 * Represents a container containing any number [DialogBodyElement]s in a [DialogMenu].
 */
class DialogBodyContainer(menu: DialogMenu) : AbstractDialogContainer<DialogBodyContainer, DialogBodyElement>(menu) {
    override fun toJson(): JsonElement = JsonArray().also { json ->
        for (element in getAllElements()) json.add(element.toJson())
    }
}