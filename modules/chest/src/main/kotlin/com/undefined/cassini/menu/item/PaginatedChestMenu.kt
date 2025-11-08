package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.PaginatedItemContainer
import com.undefined.cassini.container.item.PaginatedItemContainerImpl
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.Menu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.UUID

abstract class PaginatedChestMenu(
    title: Component,
    rows: Int,
    parent: Menu<*, *>? = null,
    override val settings: ItemMenuSettings = ItemMenuSettings(),
) : ChestMenu(title, rows, parent, settings, rootContainer = PaginatedItemContainerImpl(MAX_WIDTH, rows)), PaginatedItemContainer {

    var availableSlots: SlotIterator
        get() = paginatedRootContainer.availablePaginatedSlots
        set(value) {
            paginatedRootContainer.availablePaginatedSlots = value
        }

    private val paginatedRootContainer: PaginatedItemContainerImpl = rootContainer as PaginatedItemContainerImpl

    override fun open(player: Player, initialize: Boolean) {
        if (player.uniqueId !in viewers && initialize && paginatedRootContainer.hasCalculatedElements) {
            resetPaginatedElements()
        }

        super.open(player, initialize)
    }

    override fun update(viewer: UUID) {
        updatePaginatedElements()
        super.update(viewer)
    }

    fun updatePaginatedElements() = paginatedRootContainer.updatePageElements()
    fun resetPaginatedElements() = paginatedRootContainer.resetPageElements()

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
