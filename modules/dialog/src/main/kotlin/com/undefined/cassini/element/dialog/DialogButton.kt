package com.undefined.cassini.element.dialog

import com.google.gson.JsonObject
import com.undefined.cassini.data.dialog.CassiniDialogAction
import com.undefined.cassini.data.dialog.DialogAction
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.entity.Player
import java.util.UUID

/**
 * Represents a button inside a [com.undefined.cassini.menu.dialog.DialogMenu].
 *
 * @param label The button's text.
 * @param tooltip The tooltip shown on hover.
 * @param width The width of the button — between `1` and `1024`. Default to `150`.
 * @param action The action to perform when the button is clicked.
 */
class DialogButton(
    val label: Component,
    val tooltip: Component? = null,
    val width: Int = DEFAULT_WIDTH,
    val action: DialogAction? = CassiniDialogAction(UUID.randomUUID()),
) : AbstractDialogElement() {

    val uuid: UUID by lazy { if (action is CassiniDialogAction) action.button else UUID.randomUUID() }
    val actions: MutableList<(Player) -> Unit> = mutableListOf()

    override fun toJson() = JsonObject().also { json ->
        json.add("label", GsonComponentSerializer.gson().serializeToTree(label))
        if (tooltip != null) json.add("tooltip", GsonComponentSerializer.gson().serializeToTree(tooltip))
        json.addProperty("width", width)
        if (action != null) json.add("action", action.toJson())
    }

    fun addAction(action: (Player) -> Unit) {
        actions.add(action)
    }

    companion object {
        const val DEFAULT_WIDTH: Int = 150
    }

}