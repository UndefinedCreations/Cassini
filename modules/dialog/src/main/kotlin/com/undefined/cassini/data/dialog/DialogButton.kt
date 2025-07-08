package com.undefined.cassini.data.dialog

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.undefined.cassini.Cassini
import com.undefined.cassini.element.dialog.DialogElement
import com.undefined.cassini.menu.dialog.DialogMenu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.entity.Player
import java.util.UUID

/**
 * Represents a button inside a [DialogMenu].
 *
 * @param label The button's text.
 * @param tooltip The tooltip shown on hover.
 * @param width The width of the button — between `1` and `1024`. Default to `150`.
 * @param action The action to perform when the button is clicked.
 */
class DialogButton(
    val label: Component,
    val tooltip: Component? = null,
    val width: Int = 150,
    val action: DialogAction? = CassiniDialogAction(UUID.randomUUID()),
) : DialogElement() {

    val uuid: UUID by lazy { if (action is CassiniDialogAction) action.button else UUID.randomUUID() }
    val actions: MutableList<(Player) -> Unit> = mutableListOf()

    override fun toJson(): JsonElement = JsonObject().also { json ->
        json.add("label", GsonComponentSerializer.gson().serializeToTree(label))
        if (tooltip != null) json.add("tooltip", GsonComponentSerializer.gson().serializeToTree(tooltip))
        json.addProperty("width", width)
        if (action != null) json.add("action", action.toJson())
    }

    fun addAction(action: (Player) -> Unit) {
        actions.add(action)
    }

}