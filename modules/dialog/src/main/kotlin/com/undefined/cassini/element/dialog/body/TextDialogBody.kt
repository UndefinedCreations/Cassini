package com.undefined.cassini.element.dialog.body

import com.google.gson.JsonObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

/**
 * A [DialogBodyElement] that displays [text].
 *
 * @param text The text to display.
 * @param width The width of the text.
 */
class TextDialogBody(val text: Component, val width: Int) : DialogBodyElement("minecraft:plain_message") {
    override fun toJson() = JsonObject().apply {
        add("contents", GsonComponentSerializer.gson().serializeToTree(text))
        addProperty("width", width)
    }
}