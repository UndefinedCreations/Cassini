package com.undefined.cassini.element.dialog

import com.google.gson.JsonElement
import com.undefined.cassini.element.Element
import com.undefined.cassini.menu.dialog.DialogMenu

/**
 * Represents an element in a [DialogMenu].
 */
abstract class DialogElement : Element() {
    abstract fun toJson(): JsonElement
}