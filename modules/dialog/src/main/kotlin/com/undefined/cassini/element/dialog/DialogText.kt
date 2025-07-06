package com.undefined.cassini.element.dialog

import com.undefined.cassini.element.dialog.body.DialogBodyElement
import net.kyori.adventure.text.Component

/**
 * A piece of text in a dialog, usually used inside a [DialogBodyElement].
 *
 * @param text The text to display.
 * @param width The width of the text.
 */
data class DialogText(val text: Component, val width: Int)