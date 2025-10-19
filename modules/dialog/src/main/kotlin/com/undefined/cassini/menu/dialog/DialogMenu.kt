package com.undefined.cassini.menu.dialog

import com.google.gson.JsonObject
import com.undefined.cassini.container.dialog.DialogBodyContainer
import com.undefined.cassini.container.dialog.DialogInputContainer
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.entity.Player

/**
 * Represents a Minecraft dialog. Dialogs are simple modal windows that can display information and receive player input.
 *
 * // TODO-DOCS
 *
 * @see <a href="https://minecraft.wiki/w/Dialog">https://minecraft.wiki/w/Dialog</a>
 */
abstract class DialogMenu(
    val type: String,
    title: Component,
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : CassiniMenu<DialogMenu, DialogMenuSettings>(title, parent) {

    val bodyContainer: DialogBodyContainer = DialogBodyContainer()
    val inputContainer: DialogInputContainer = DialogInputContainer()
    open val totalButtons: List<DialogButton>
        get() = bodyContainer.elements.filterIsInstance<DialogButton>() // TODO

    override fun open(player: Player) {
        if (player.uniqueId !in viewers) initialize(player)
        super.open(player)

        NMSManager.nms.showDialog(player, toJson())
    }

    open fun toJson(): JsonObject = JsonObject().also { json ->
        json.addProperty("type", type)
        json.add("title", GsonComponentSerializer.gson().serializeToTree(title))
        json.add("external_title", GsonComponentSerializer.gson().serializeToTree(settings.externalTitle))
        json.add("body", bodyContainer.toJson())
        json.add("inputs", inputContainer.toJson())
        json.addProperty("can_close_with_escape", settings.canCloseWithEscape)
        json.addProperty("after_action", "close") // TODO make this an enum https://minecraft.wiki/w/Dialog#Dialog_format
    }

}