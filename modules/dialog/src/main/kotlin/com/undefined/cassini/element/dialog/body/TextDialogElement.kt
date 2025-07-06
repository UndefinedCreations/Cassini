package com.undefined.cassini.element.dialog.body

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

/**
 * A [DialogBodyElement] that displays [text].
 *
 * @param text The text to display.
 * @param width The width of the text.
 */
class TextDialogElement(val text: Component, val width: Int) : DialogBodyElement() {
    override fun toJson(): JsonElement = JsonObject().apply {
        addProperty("type", "minecraft:plain_message")
        add("contents", GsonComponentSerializer.gson().serializeToTree(text))
        addProperty("width", width)
    }
}