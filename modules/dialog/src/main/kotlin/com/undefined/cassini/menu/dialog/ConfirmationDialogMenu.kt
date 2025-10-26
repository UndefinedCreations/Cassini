package com.undefined.cassini.menu.dialog

import com.undefined.cassini.data.dialog.DialogMenuSettings
import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component

/**
 * A dialog screen with two action buttons in footer, specified by [yes] and [no].
 * // TODO-DOCS
 */
open class ConfirmationDialogMenu(
    title: Component,
    val yes: DialogButton,
    val no: DialogButton,
    parent: CassiniMenu<*, *>? = null,
    override val settings: DialogMenuSettings = DialogMenuSettings(title),
) : DialogMenu("minecraft:confirmation", title, parent, settings) {

    override val totalButtons: List<DialogButton>
        get() = super.totalButtons + yes + no

    override fun toJson() = super.toJson().also { json ->
        json.add("yes", yes.toJson())
        json.add("no", no.toJson())
    }

}