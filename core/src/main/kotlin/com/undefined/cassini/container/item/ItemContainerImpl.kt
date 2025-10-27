package com.undefined.cassini.container.item

import com.undefined.cassini.container.Container
import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.item.ItemMenu
import org.jetbrains.annotations.Range
import kotlin.collections.plus

/**
 * The primary implementation of [ItemContainer]; representing a container in an [ItemMenu]. Any null values are simply empty items.
 */
open class ItemContainerImpl(
    val width: @Range(from = 1, to = 9) Int,
    val height: @Range(from = 1, to = 6) Int,
) : Container<ItemContainerImpl, ItemElement?>, ItemContainer<ItemContainerImpl> {

    /**
     * The menu this container is in. This is required for [update] calls.
     */
    lateinit var menu: ItemMenu<*>

//    private val slotIterator: BoxSlotIterator by lazy { BoxSlotIterator(menu, startSlot = 0, rows, columns) }
    private val elements: Array<Array<ItemElement?>> = Array(height) { Array(width) { null } } // grid map of elements
    private val containers: LinkedHashMap<Int, ItemContainerImpl> = linkedMapOf() // slot to container

    override fun removeElement(row: Int, column: Int) {
        elements[row][column] = null
    }

    override fun setElement(row: Int, column: Int, element: ItemElement) {
//        require(row <= slotIterator.width)
//        require(column <= slotIterator.width)

        elements[row][column] = element
        element.containers.add(this)
    }

    override fun addElement(element: ItemElement) {
        TODO("Use the next available slot")
    }

    fun addContainer(slot: Int, container: ItemContainerImpl) {
        containers[slot] = container
        container.menu = menu
    }

    override fun addContainer(container: ItemContainerImpl) = addContainer(slot = 0, container)

    override fun calculateCoordinateFromSlot(slot: Int): CartesianCoordinate = CartesianCoordinate(x = slot % width, y = slot / width)

    override fun update() {
        menu.update()
    }

    /**
     * Gets item all elements, including from sub-containers.
     *
     * @param startSlot The slot the container is located at. **By default, makes the assumption that the container is located at slot `0`.**
     */
    @Suppress("UNCHECKED_CAST")
    fun getAllElementsWithSlots(startSlot: Int = 0): Map<Int, ItemElement> {
        val allElements = getElementsWithSlots(startSlot).toMutableMap()
        for ((slot, container) in containers) {
            val containerElements = container.getAllElementsWithSlots(slot)
            allElements.putAll(containerElements)
        }
        return allElements
    }

    /**
     * Gets current container elements.
     *
     * @param startSlot The slot the container is located at.
     */
    @Suppress("UNCHECKED_CAST")
    fun getElementsWithSlots(startSlot: Int): Map<Int, ItemElement> {
        val map = mutableMapOf<Int, ItemElement>()
        for (row in elements.indices) {
            for (column in elements[row].indices) {
                val element = elements[row][column] ?: continue
                val slot = startSlot + row * menu.maxWidth + column
                map[slot] = element
            }
        }
        return map
    }

    /**
     * Gets all elements, including from sub-containers. Elements that are `null` are empty slots.
     */
    override fun getAllElements(): List<ItemElement?> {
        val allElements = getAllElementsWithSlots()

        val currentElements: MutableList<ItemElement?> = mutableListOf()
        for (slot in 0..menu.size) {
            currentElements.add(allElements[slot])
        }

        return currentElements
    }

}