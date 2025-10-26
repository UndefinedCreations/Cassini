package com.undefined.cassini.menu.item

import com.undefined.cassini.container.item.ItemContainer
import com.undefined.cassini.container.item.ItemContainerImpl
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.Menu
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
    parent: Menu<*, *>?,
    type: MenuType,
    maxWidth: Int,
    protected val rootContainer: ItemContainerImpl,
) : ItemMenu<T>(title, size, parent, type, maxWidth), ItemContainer<ItemContainerImpl> by rootContainer {

    override val elements: Map<Int, ItemElement>
        get() = rootContainer.getAllElementsWithSlots()

    override fun update() {
        super<ItemMenu>.update()
    }
    override fun getItems(player: Player): List<ItemStack> = rootContainer.getAllElements().map { it?.getItem(player) ?: ItemStack(Material.AIR) }

}