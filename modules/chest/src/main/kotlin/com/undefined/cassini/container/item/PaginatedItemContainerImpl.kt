package com.undefined.cassini.container.item

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import kotlin.math.ceil

/**
 * An [ItemContainerImpl] designed for a [PaginatedChestMenu].
 */
class PaginatedItemContainerImpl(
    width: Int,
    height: Int,
) : ItemContainerImpl(width, height), PaginatedItemContainer {

    /**
     * Slots available to be filled with paginated elements.
     */
    lateinit var availablePaginatedSlots: SlotIterator

    /**
     * Whether the container has calculated any page elements. This value is updated in [updatePageElements].
     */
    var hasCalculatedElements: Boolean = false

    /**
     * Contains all elements to be added in pages.
     */
    private val paginatedElements: MutableList<ItemElement?> = mutableListOf()
    private val currentPage: MutableMap<Int, ItemElement?> = LinkedHashMap(availablePaginatedSlots.numberOfSlots())

    private var currentPageNumber = 1

    override fun addPaginatedElements(elements: List<ItemElement>) {
        paginatedElements.addAll(elements)
    }

    /**
     * Replaces old page elements with new elements according to [currentPageNumber].
     */
    fun updatePageElements() {
        hasCalculatedElements = true

        val contents = getPageContents(currentPageNumber)

        currentPage.clear()
        availablePaginatedSlots.calculateSlots()
        for ((i, slot) in availablePaginatedSlots.slots.withIndex()) {
            if (i > contents.lastIndex) break
            val element = contents[i]
            currentPage[slot] = element

            if (element != null) setElement(slot, element) else removeElement(slot)
        }
    }

    /**
     * Goes to the next page.
     *
     * @return `false` if there is no next page.
     */
    fun next(): Boolean {
        if (currentPageNumber + 1 > numberOfPages()) return false

        currentPageNumber++
        updatePageElements()
        return true
    }

    /**
     * Goes to the previous page if possible
     *
     * @return `false` if there is no previous page.
     */
    fun previous(): Boolean {
        if (currentPageNumber - 1 == 0) return false

        currentPageNumber--
        updatePageElements()
        return true
    }

    /**
     * Returns the number of pages this menu has.
     */
    fun numberOfPages(): Int = ceil(paginatedElements.size.toDouble() / availablePaginatedSlots.numberOfSlots()).toInt()

    /**
     * Returns a list with all the item elements to be displayed in the given page.
     */
    private fun getPageContents(pageNumber: Int): MutableList<ItemElement?> {
        val actualPageNumber = pageNumber - 1
        val pageElements: MutableList<ItemElement?> = mutableListOf()
        val numberOfSlots = availablePaginatedSlots.numberOfSlots()

        var max: Int = ((actualPageNumber * numberOfSlots) + numberOfSlots)
        if (max > paginatedElements.size) max = paginatedElements.size

        for (i in actualPageNumber * numberOfSlots..<max) {
            pageElements.add(paginatedElements[i])
        }

        return pageElements
    }

}