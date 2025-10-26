package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.PaginatedItemContainer
import com.undefined.cassini.container.item.PaginatedItemContainerImpl
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.Menu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import net.kyori.adventure.text.Component
import java.util.UUID

abstract class PaginatedChestMenu(
    title: Component,
    rows: Int,
    parent: Menu<*, *>? = null,
    override val settings: ItemMenuSettings = ItemMenuSettings(),
) : ChestMenu(title, rows, parent, settings, rootContainer = PaginatedItemContainerImpl(MAX_WIDTH, MAX_HEIGHT)), PaginatedItemContainer {

    var availableSlots: SlotIterator
        get() = paginatedRootContainer.availableSlots
        set(value) {
            paginatedRootContainer.availableSlots = value
        }

    private val paginatedRootContainer: PaginatedItemContainerImpl = rootContainer as PaginatedItemContainerImpl

    override fun update(viewer: UUID) {
        if (!paginatedRootContainer.hasCalculatedElements) paginatedRootContainer.updatePageElements()
        super.update(viewer)
    }

    override fun addPaginatedElements(elements: List<ItemElement>) = paginatedRootContainer.addPaginatedElements(elements)

    /**
     * Goes to the next page.
     *
     * @return `false` if there is no next page.
     */
    fun next(): Boolean = paginatedRootContainer.next().also { update() }

    /**
     * Goes to the previous page if possible
     *
     * @return `false` if there is no previous page.
     */
    fun previous(): Boolean = paginatedRootContainer.previous().also { update() }

}
