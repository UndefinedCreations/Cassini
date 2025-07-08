package com.undefined.cassini.menu.dialog

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.undefined.cassini.container.dialog.DialogBodyContainer
import com.undefined.cassini.data.dialog.DialogButton
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.menu.CassiniMenu
import com.undefined.cassini.internal.NMSManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.entity.Player

abstract class DialogMenu(
    val type: String,
    title: Component,
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : CassiniMenu<DialogMenu, DialogMenuSettings>(title, parent) {

    val bodyContainer: DialogBodyContainer = DialogBodyContainer()
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
        json.add("inputs", JsonArray())
        json.addProperty("can_close_with_escape", settings.canCloseWithEscape)
        json.addProperty("after_action", "close") // TODO make this an enum https://minecraft.wiki/w/Dialog#Dialog_format
    }

    companion object {
        const val MAX_WIDTH: Int = 9
        const val MAX_HEIGHT: Int = 6
    }

}