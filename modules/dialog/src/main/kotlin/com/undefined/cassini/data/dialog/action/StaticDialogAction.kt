package com.undefined.cassini.data.dialog.action

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.undefined.cassini.menu.dialog.DialogMenu
import org.bukkit.NamespacedKey

/**
 * Static actions are actions that don't depend on the value of an input field.
 */
sealed class StaticDialogAction(type: String) : DialogAction(type) {

    class OpenURL(val url: String) : DialogAction("open_url") {
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.addProperty("url", url)
        }
    }

    class RunCommand(val command: String) : DialogAction("run_command") {
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.addProperty("command", command)
        }
    }

    class SuggestCommand(val command: String) : DialogAction("suggest_command") {
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.addProperty("command", command)
        }
    }

    class CopyToClipboard(val text: String) : DialogAction("copy_to_clipboard") {
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.addProperty("value", text)
        }
    }

    class ShowDialog(val dialog: JsonElement) : DialogAction("show_dialog") {
        constructor(dialog: DialogMenu) : this(dialog.toJson())
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.add("dialog", dialog)
        }
    }

    class Custom(val id: NamespacedKey, val payload: String) : DialogAction("custom") {
        constructor(id: String, payload: String) : this(
            NamespacedKey.fromString(id) ?: throw IllegalArgumentException("Could not parse NamespacedKey!"), payload
        )
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.addProperty("id", id.toString())
            json.addProperty("payload", payload)
        }
    }

}