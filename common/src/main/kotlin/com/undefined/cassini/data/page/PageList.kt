package com.undefined.cassini.data.page

import com.undefined.cassini.data.item.GUIItem
import org.jetbrains.annotations.ApiStatus
import java.util.*
import kotlin.math.ceil
import kotlin.math.min

@ApiStatus.Internal
class PageList(collection: List<GUIItem>, val maxElement: Int) : ArrayList<GUIItem>(collection) {

    /**
     * Calculates the number of pages needed to display the elements in the collection based on the maximum number of elements per page.
     *
     * @return The number of pages.
     */
    fun pageCount(): Int = ceil(size.toDouble() / maxElement).toInt()

    /**
     * Retrieves a page of items from a page list.
     *
     * @param page The page number to retrieve.
     * @return The list of items on the requested page, or null if the page number is out of range.
     */
    fun getPage(page: Int): List<GUIItem>? {
        if (page < 1 || page > pageCount()) return null;
        if (isEmpty()) return null
        val startIndex = (page - 1) * maxElement
        val endIndex = min(startIndex + maxElement, size)
        return subList(startIndex, endIndex)
    }

    /**
     * Adds all elements from the given array to the PageList.
     *
     * @param item an array of elements of type GUIItem to be added.
     */
    fun addAll(item: Array<GUIItem>) {
        Collections.addAll(this, *item)
    }

}