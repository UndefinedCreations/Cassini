package com.undefined.cassini.data.dialog.action

import com.google.gson.JsonObject
import org.bukkit.NamespacedKey

/**
 * Actions that can be used in conjunction with input controls.
 */
sealed class DynamicDialogAction(type: String) : DialogAction(type) {

    /**
     * @param template A string with a macro template to be interpreted as a command.
     */
    class RunCommand(val template: String) : DialogAction("dynamic/run_command") {
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.addProperty("template", template)
        }
    }

    /**
     * @param additions Fields to be added to payload.
     */
    class Custom(val id: NamespacedKey, val additions: JsonObject? = null) : DialogAction("dynamic/custom") {
        constructor(id: String, payload: JsonObject? = null) : this(
            NamespacedKey.fromString(id) ?: throw IllegalArgumentException("Could not parse NamespacedKey!"), payload
        )
        override fun toJson(): JsonObject = super.toJson().also { json ->
            json.addProperty("id", id.toString())
            json.add("additions", additions)
        }
    }

}