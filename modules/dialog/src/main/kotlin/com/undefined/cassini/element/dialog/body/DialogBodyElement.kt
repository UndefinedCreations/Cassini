package com.undefined.cassini.element.dialog.body

import com.undefined.cassini.element.dialog.DialogElement
import com.undefined.cassini.menu.dialog.DialogMenu

/**
 * Represents a body element in a [DialogMenu].
 *
 * @param type The element type (e.g. `minecraft:plain_message`, `minecraft:text`).
 */
abstract class DialogBodyElement(type: String) : DialogElement(type)