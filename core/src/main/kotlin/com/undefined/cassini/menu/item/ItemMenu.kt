package com.undefined.cassini.menu.item

import com.undefined.cassini.CassiniConfig
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.data.item.ClickData
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.menu.Menu
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

/**
 * Represents a menu that contains items.
 */
@Suppress("UNCHECKED_CAST")
abstract class ItemMenu<T : ItemMenu<T>>(
    title: Component,
    val size: Int,
    parent: Menu<*, *>?,
    type: MenuType,
    val maxWidth: Int,
) : Menu<T, ItemMenuSettings>(title, parent, type) {

    val items: MutableList<ItemStack> = mutableListOf() // TODO make AIR items just be null

    /**
     * Whether the menu is solely sent through packets instead of doing much server-side work.
     */
    val packetBased: Boolean = true // TODO change to default false when adding non-packet based menus

    abstract val elements: Map<Int, ItemElement> // slot to element

    val clickActions: MutableList<(ClickData<T>) -> Unit> = mutableListOf() // int is slot
    val closeActions: MutableList<(Player) -> Unit> = mutableListOf() // int is slot

    override val settings: ItemMenuSettings = ItemMenuSettings()

    override fun open(player: Player) {
        if (player.uniqueId !in viewers) initialize(player)
        super.open(player)

        NMSManager.nms.sendOpenScreenPacket(player, type, title)
        update(player.uniqueId)
    }

    override fun update(viewer: UUID) {
        val player = Bukkit.getPlayer(viewer) ?: return

        updateItems(player)
        NMSManager.nms.sendContentsPacket(player, items)
    }

    /**
     * Returns the contents of this menu.
     */
    abstract fun getItems(player: Player): List<ItemStack>

    /**
     * Updates the [items] list with [getItems].
     */
    fun updateItems(player: Player) {
        items.clear()
        items.addAll(getItems(player))
    }

    fun preventClicking() {
        onClick { cancel() }
    }

    fun onClick(action: ClickData<T>.() -> Unit): T = apply {
        clickActions.add(action)
    } as T

    fun onClose(action: (Player) -> Unit): T = apply {
        closeActions.add(action)
    } as T

    fun createClickData(player: Player, slot: Int): ClickData<T> = ClickData(this as T, player, slot)

    // TODO rename this to onClick and make it overridable and, by default, run all clickActions pertaining to slots and then all the element click actions
    fun callClickActions(clickData: ClickData<*>) {
        for (clickAction in clickActions) {
            val data = clickData as? ClickData<T> ?: continue
            clickAction(data)
        }

        Bukkit.getScheduler().runTask(CassiniConfig.plugin, Runnable {
            elements[clickData.slot]?.callActions(clickData)
        })
    }

}