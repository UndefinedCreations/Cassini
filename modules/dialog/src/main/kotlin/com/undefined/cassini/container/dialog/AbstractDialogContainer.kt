package com.undefined.cassini.container.dialog

import com.google.gson.JsonElement
import com.undefined.cassini.container.Container
import com.undefined.cassini.container.SimpleContainerImpl
import com.undefined.cassini.element.Element
import com.undefined.cassini.menu.dialog.DialogMenu

abstract class AbstractDialogContainer<C : Container<C, *>, E : Element?>(val menu: DialogMenu) : SimpleContainerImpl<C, E>() {
    override fun update() = menu.update()
    abstract fun toJson(): JsonElement
}