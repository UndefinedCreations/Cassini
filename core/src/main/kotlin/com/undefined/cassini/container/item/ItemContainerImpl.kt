package com.undefined.cassini.container.item

import com.undefined.cassini.container.SimpleContainerImpl
import com.undefined.cassini.element.CartesianCoordinate
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ItemMenu
import kotlin.collections.plus

/**
 * The primary implementation of [ItemContainer]; representing a container in an [ItemMenu]. Any null values are simply empty items.
 */
open class ItemContainerImpl(
    val cartesianCoordinate: CartesianCoordinate? = null,
    val slot: Int? = null,
    val width: Int,
    val height: Int,
) : SimpleContainerImpl<ItemContainerImpl, ItemElement?>(), ItemContainer<ItemContainerImpl> {

    lateinit var menu: ItemMenu<*>
    protected val itemElements: HashMap<Int, ItemElement> = hashMapOf() // slot to element

    constructor(x: Int, y: Int, width: Int, height: Int) : this(CartesianCoordinate(x, y), null, width, height)
    constructor(slot: Int, width: Int, height: Int) : this(null, slot, width, height)

    override fun removeElement(slot: Int) {
        itemElements.remove(slot)
    }

    override fun setElement(slot: Int, element: ItemElement) {
        itemElements[slot] = element
        element.containers.add(this)
    }

    override fun addElement(element: ItemElement) {
        TODO("Use the next available slot")
    }

    /**
     * Gets item all elements, including from sub-containers.
     */
    @Suppress("UNCHECKED_CAST")
    fun getAllElementsWithSlots(): Map<Int, ItemElement> = (itemElements.clone() as HashMap<Int, ItemElement>).also {
        for (elements in containers.map { it.getAllElementsWithSlots() }) it + elements
    }

    override fun update() {
        menu.update()
    }

    /**
     * Gets all elements, including from sub-containers. Elements that are `null` are empty slots.
     */
    override fun getAllElements(): List<ItemElement?> {
        if (itemElements.isEmpty()) return emptyList()
        val highestSlot = itemElements.maxBy { it.key }.key

        val currentElements: MutableList<ItemElement?> = mutableListOf() // elements in this container
        for (slot in 0..highestSlot) currentElements.add(itemElements[slot])

        return currentElements + containers.flatMap { it.getAllElements() }
    }

}