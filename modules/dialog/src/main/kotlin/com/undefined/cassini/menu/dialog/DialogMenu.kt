package com.undefined.cassini.menu.dialog

import com.google.gson.JsonObject
import com.undefined.cassini.container.dialog.DialogBodyContainer
import com.undefined.cassini.container.dialog.DialogInputContainer
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.menu.Menu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

/**
 * Represents a Minecraft dialog. Dialogs are simple modal windows that can display information and receive player input.
 *
 * // TODO-DOCS
 *
 * @see <a href="https://minecraft.wiki/w/Dialog">https://minecraft.wiki/w/Dialog</a>
 */
abstract class DialogMenu(
    val dialogType: String,
    title: Component,
    parent: Menu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : Menu<DialogMenu, DialogMenuSettings>(title, parent, MenuType.DIALOG) {

    val bodyContainer: DialogBodyContainer = DialogBodyContainer(this)
    val inputContainer: DialogInputContainer = DialogInputContainer(this)
    open val totalButtons: List<DialogButton>
        get() = bodyContainer.getAllElements().filterIsInstance<DialogButton>() // TODO

    override fun open(player: Player) {
        if (player.uniqueId !in viewers) initialize(player)
        super.open(player)

        update(player.uniqueId)
    }

    open fun toJson(): JsonObject = JsonObject().also { json ->
        json.addProperty("type", dialogType)
        json.add("title", GsonComponentSerializer.gson().serializeToTree(title))
        json.add("external_title", GsonComponentSerializer.gson().serializeToTree(settings.externalTitle))
        json.add("body", bodyContainer.toJson())
        json.add("inputs", inputContainer.toJson())
        json.addProperty("can_close_with_escape", settings.canCloseWithEscape)
        json.addProperty("after_action", settings.afterAction.tagName)
    }

    override fun update(viewer: UUID) {
        val player = Bukkit.getPlayer(viewer) ?: error("Could not find player")
        player.closeInventory()
        NMSManager.nms.showDialog(player, toJson())
    }

}