package com.undefined.cassini.element.dialog.input

import com.undefined.cassini.element.dialog.DialogElement
import com.undefined.cassini.menu.dialog.DialogMenu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

/**
 * Represents an input element in a [DialogMenu].
 *
 * @param type The element type (e.g. `minecraft:plain_message`, `minecraft:text`).
 * @param key A string identifier of value used when submitting data, must be a valid template argument (letters, digits and _).
 * @param label The label to be displayed to the left or right.
 */
abstract class DialogInputElement(type: String, val key: String, val label: Component) : DialogElement(type) {
    override fun toJson() = super.toJson().apply {
        addProperty("key", key)
        add("label", GsonComponentSerializer.gson().serializeToTree(label))
    }
}