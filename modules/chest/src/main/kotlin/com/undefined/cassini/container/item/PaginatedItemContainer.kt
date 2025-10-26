package com.undefined.cassini.container.item

import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.item.ItemMenu
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import kotlin.collections.addAll
import kotlin.math.ceil

/**
 * An [ItemContainer] designed for a [PaginatedChestMenu].
 */
class PaginatedItemContainer(
    menu: ItemMenu<*>,
    width: Int,
    height: Int,
) : ItemContainer(menu, 0, 0, width, height) {

    lateinit var availableSlots: SlotIterator

    /**
     * Whether the container has calculated any page elements. This value is updated in [updatePageElements].
     */
    var hasCalculatedElements: Boolean = false

    private val size = width * height

    /**
     * Contains all elements to be added in pages.
     */
    private val paginatedElements: MutableList<ItemElement?> = mutableListOf()
    private val currentPage: MutableMap<Int, ItemElement?> = LinkedHashMap(size)

    private var currentPageNumber = 1

    /**
     * Adds an element to [paginatedElements].
     */
    fun addPaginatedElement(element: ItemElement) {
        paginatedElements.add(element)
    }

    /**
     * Adds a number of elements to [paginatedElements].
     */
    fun addPaginatedElements(element: List<ItemElement>) {
        paginatedElements.addAll(element)
    }

    /**
     * Adds a number of elements to [paginatedElements].
     */
    fun addPaginatedElements(vararg element: ItemElement) {
        paginatedElements.addAll(element)
    }

    /**
     * Replaces old page elements with new elements according to [currentPageNumber].
     */
    fun updatePageElements() {
        hasCalculatedElements = true

        val contents = getPageContents(currentPageNumber)

        currentPage.clear()
        availableSlots.calculateSlots()
        for ((i, slot) in availableSlots.slots.withIndex()) {
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
    fun numberOfPages(): Int = ceil(paginatedElements.size.toDouble() / availableSlots.numberOfSlots()).toInt()

    /**
     * Returns a list with all the item elements to be displayed in the given page.
     */
    private fun getPageContents(pageNumber: Int): MutableList<ItemElement?> {
        val actualPageNumber = pageNumber - 1
        val pageElements: MutableList<ItemElement?> = mutableListOf()
        val numberOfSlots = availableSlots.numberOfSlots()

        var max: Int = ((actualPageNumber * numberOfSlots) + numberOfSlots)
        if (max > paginatedElements.size) max = paginatedElements.size

        for (i in actualPageNumber * numberOfSlots..<max) {
            pageElements.add(paginatedElements[i])
        }

        return pageElements
    }

}