package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.ItemContainer
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * An [ItemMenu] that only contains one root container.
 */
@Suppress("UNCHECKED_CAST")
abstract class SingleContainerItemMenu<T : SingleContainerItemMenu<T>>(
    title: Component,
    size: Int,
    parent: CassiniMenu<*, *>?,
    type: MenuType,
    val maxWidth: Int
) : ItemMenu<T>(title, size, parent, type) {

    abstract val rootContainer: ItemContainer

    // BASE
    fun addContainer(container: ItemContainer): T = apply {
        rootContainer.addContainer(container)
    } as T

    override fun getItems(player: Player): List<ItemStack> {
        val itemsWithSlots: HashMap<Int, ItemStack> = hashMapOf()
        for (element in rootContainer.getAllElements<ItemElement>())
            itemsWithSlots[element.computeSlot(maxWidth)] = element.getItem(player)

        val items: MutableList<ItemStack> = mutableListOf()
        for (slot in 0..size) items.add(itemsWithSlots.getOrDefault(slot, ItemStack(Material.AIR)))
        return items
    }

}