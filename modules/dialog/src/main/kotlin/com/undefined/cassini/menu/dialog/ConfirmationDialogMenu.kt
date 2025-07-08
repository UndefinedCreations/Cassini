package com.undefined.cassini.menu.dialog

import com.undefined.cassini.data.dialog.DialogButton
import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component

open class ConfirmationDialogMenu(
    title: Component,
    val yesButton: DialogButton,
    val noButton: DialogButton,
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : DialogMenu("minecraft:confirmation", title, parent, settings) {

    override val buttons: List<DialogButton>
        get() = super.buttons + yesButton + noButton

    override fun toJson() = super.toJson().also { json ->
        json.add("yes", yesButton.toJson())
        json.add("no", noButton.toJson())
    }

}