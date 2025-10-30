package com.undefined.cassini.container.item

import com.undefined.cassini.element.item.ItemElement

/**
 * Operations for filling areas of an item container with elements.
 */
interface FillOperations {

    fun setElement(row: Int, column: Int, element: ItemElement)
    fun removeElement(row: Int, column: Int)

    /**
     * Fills the border of the container with [element].
     */
    fun fillBorder(element: ItemElement)

    /**
     * Fills the entire container with [element].
     */
    fun fill(element: ItemElement)

    /**
     * Fills a rectangular area with the specified item. Rows and columns specified are inclusive.
     */
    fun fill(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int, element: ItemElement) {
        for (row in fromRow..toRow) {
            for (column in fromColumn..toColumn) {
                setElement(row, column, element)
            }
        }
    }

    /**
     * Clears all items in the specified rectangular area. Rows and columns specified are inclusive.
     */
    fun clear(fromRow: Int, fromColumn: Int, toRow: Int, toColumn: Int) {
        for (row in fromRow..toRow) {
            for (column in fromColumn..toColumn) {
                removeElement(row, column)
            }
        }
    }

    /**
     * Fills the specified row with [element].
     */
    fun fillRow(row: Int, element: ItemElement)

    /**
     * Fills the specified column with [element].
     */
    fun fillColumn(column: Int, element: ItemElement)

}