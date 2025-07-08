package com.undefined.cassini.data.dialog

import java.util.UUID

/**
 * A click action that allows you to run a button's click actions.
 */
class CassiniDialogAction(val button: UUID) : StaticDialogAction("run_command") {
    override fun toJson() = super.toJson().also { json ->
        json.addProperty("command", "_cassini_dialog click $button ' '")
    }
}