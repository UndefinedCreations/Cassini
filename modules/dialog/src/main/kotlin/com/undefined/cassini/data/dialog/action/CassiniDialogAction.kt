package com.undefined.cassini.data.dialog.action

import com.google.gson.JsonObject
import java.util.UUID

/**
 * A click action that allows you to run a button's click actions.
 */
class CassiniDialogAction(val button: UUID) : DialogAction("custom") {
    override fun toJson(): JsonObject = super.toJson().also { json ->
        json.addProperty("id", "cassini:$button")
    }
}