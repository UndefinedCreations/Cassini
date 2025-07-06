package com.undefined.cassini.menu.dialog

import com.undefined.cassini.data.dialog.DialogButton
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component

open class NoticeDialogMenu(
    title: Component,
    val button: DialogButton = DialogButton(Component.translatable("gui.ok")),
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : DialogMenu("minecraft:notice", title, parent, settings) {
    override fun toJson() = super.toJson().also { json ->
        json.add("action", button.toJson())
    }
}