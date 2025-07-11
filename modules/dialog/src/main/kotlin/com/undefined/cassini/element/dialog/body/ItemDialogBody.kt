package com.undefined.cassini.element.dialog.body

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.undefined.cassini.element.dialog.DialogText
import com.undefined.cassini.internal.NMSManager
import org.bukkit.inventory.ItemStack

/**
 * A [DialogBodyElement] that displays an item.
 *
 * @param item The item displayed.
 * @param description The description of the item, that is shown when hovered. If null, the no hover text will be shown.
 * @param showDecoration If true, count and damage bar will be rendered over the item. Defaults to `true`.
 * @param showTooltip If true, item tooltip will show up when item is hovered. Defaults to `true`.
 * @param width Value between `1` and `256` — Horizontal size of element. Defaults to `16`.
 * @param height Value between `1` and `256` — Vertical size of element. Defaults to `16`.
 */
class ItemDialogBody(
    val item: ItemStack,
    val description: DialogText? = null,
    val showDecoration: Boolean = true,
    val showTooltip: Boolean = true,
    val width: Int = 16,
    val height: Int = 16,
) : DialogBodyElement("minecraft:item") {
    override fun toJson() = JsonObject().also { json ->
        json.add("item", NMSManager.nms.encodeItemStack(item))
        json.addProperty("show_decoration", showDecoration)
        json.addProperty("show_tooltip", showTooltip)
        json.addProperty("width", width)
        json.addProperty("height", height)
    }
}