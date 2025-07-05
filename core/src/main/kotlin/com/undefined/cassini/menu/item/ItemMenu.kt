package com.undefined.cassini.menu.item

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.data.item.ClickData
import com.undefined.cassini.menu.CassiniMenu
import com.undefined.cassini.nms.NMSManager
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Represents a menu that contains items.
 */
@Suppress("UNCHECKED_CAST")
abstract class ItemMenu<T : ItemMenu<T>>(
    title: Component,
    val size: Int,
    parent: CassiniMenu<*, *>?,
    val type: MenuType,
) : CassiniMenu<T, ItemMenuSettings>(title, parent) {

    val clickActions: MutableList<(ClickData<T>) -> Unit> = mutableListOf() // int is slot
    val closeActions: MutableList<(Player) -> Unit> = mutableListOf() // int is slot

    override val settings: ItemMenuSettings = ItemMenuSettings()

    override fun open(player: Player) {
        if (player.uniqueId !in viewers) initialize(player)
        super.open(player)

        NMSManager.nms.sendOpenScreenPacket(player, type, title)
        NMSManager.nms.sendContentsPacket(player, getItems(player))
    }

    /**
     * Returns the contents of this menu.
     */
    abstract fun getItems(player: Player): List<ItemStack>

    fun onClick(action: (ClickData<T>) -> Unit): T = apply {
        clickActions.add(action)
    } as T

    fun onClose(action: (Player) -> Unit): T = apply {
        closeActions.add(action)
    } as T

    fun createClickData(player: Player, slot: Int): ClickData<T> = ClickData(this as T, player, slot)

    fun callClickActions(clickData: ClickData<*>) {
        for (clickAction in clickActions) {
            val data = clickData as? ClickData<T> ?: continue
            clickAction(data)
        }
    }

}