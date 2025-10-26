package com.undefined.cassini.element.dialog

import com.google.gson.JsonObject
import com.undefined.cassini.element.Element

/**
 * Represents *any* element displayed in a dialog. This includes dialog buttons, body elements, and input elements.
 */
abstract class AbstractDialogElement() : Element() {
    abstract fun toJson(): JsonObject
}