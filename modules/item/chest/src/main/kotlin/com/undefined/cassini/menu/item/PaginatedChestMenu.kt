package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.PaginatedItemContainer
import com.undefined.cassini.menu.CassiniMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import net.kyori.adventure.text.Component
import java.util.UUID

abstract class PaginatedChestMenu(
    title: Component,
    rows: Int,
    parent: CassiniMenu<*, *>? = null,
    override val settings: ItemMenuSettings = ItemMenuSettings(),
) : ChestMenu(title, rows, parent, settings) {

    override val rootContainer: PaginatedItemContainer = PaginatedItemContainer(MAX_WIDTH, MAX_HEIGHT)

    var availableSlots: SlotIterator
        get() = rootContainer.availableSlots
        set(value) {
            rootContainer.availableSlots = value
        }

    override fun update(viewer: UUID) {
        if (!rootContainer.hasCalculatedElements) rootContainer.updatePageElements()
        super.update(viewer)
    }

    /**
     * Goes to the next page.
     *
     * @return `false` if there is no next page.
     */
    fun next(): Boolean = rootContainer.next().also { update() }

    /**
     * Goes to the previous page if possible
     *
     * @return `false` if there is no previous page.
     */
    fun previous(): Boolean = rootContainer.previous().also { update() }

}
