package com.undefined.cassini.menu.item

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.internal.NMS
import com.undefined.cassini.menu.CassiniMenu
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

/**
 * Represents a menu that contains items.
 */
abstract class ItemMenu<T : ItemMenu<T>>(
    title: Component,
    val size: Int,
    parent: CassiniMenu<*, *>?,
    val type: MenuType,
) : CassiniMenu<T, ItemMenuSettings>(title, parent) {

    abstract val nms: NMS
    override val settings: ItemMenuSettings = ItemMenuSettings()

    override fun open(player: Player) {
        if (player.uniqueId !in viewers) initialize(player)
        super.open(player)

        nms.sendOpenScreenPacket(player, type, title)
        nms.sendContentsPacket(player, getItems(player))
    }

    /**
     * Returns the contents of this menu.
     */
    abstract fun getItems(player: Player): List<ItemStack>

    companion object {
        const val CONTAINER_ID = 1
        const val SYNC_ID = 1

        val carriedItems: HashMap<UUID, ItemStack> = hashMapOf() // player uuid to carried item
    }

}