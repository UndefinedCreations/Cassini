package com.undefined.cassini.menu.dialog

import com.google.gson.JsonElement
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component

open class NoticeDialogMenu(
    title: Component,
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : DialogMenu("minecraft:notice", title, parent, settings) {
    override fun toJson(): JsonElement = super.toJson().also { json ->
        // TODO finish this
    }
}