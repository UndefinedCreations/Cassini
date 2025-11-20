package com.undefined.cassini.menu.item

import com.undefined.cassini.CassiniConfig
import com.undefined.cassini.container.item.ItemContainerImpl
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.data.item.ClickData
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.internal.wrapper.ItemMenuWrapper
import com.undefined.cassini.menu.Menu
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
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
    open val packetBased: Boolean = false // TODO change to default false when adding non-packet based menus
    val wrappers: HashMap<UUID, ItemMenuWrapper> = hashMapOf()

    abstract val elements: Map<Int, ItemElement> // slot to element

    protected val containers: MutableList<ItemContainerImpl> = mutableListOf()

    val clickActions: MutableList<(ClickData<T>) -> Unit> = mutableListOf() // int is slot
    val closeActions: MutableList<(Player) -> Unit> = mutableListOf() // int is slot

    override val settings: ItemMenuSettings = ItemMenuSettings()

    override fun open(player: Player, initialize: Boolean) {
        if (player.uniqueId !in viewers && initialize) {
            for (container in containers) container.clear()
            if (!packetBased) {
                println("wrappers[viewer] == null = ${wrappers[player.uniqueId] == null}")
                if (wrappers[player.uniqueId] == null) setupWrapper(player)
                val wrapper = wrappers[player.uniqueId] ?: error("Wrapper is null??!")
                NMSManager.nms.setContainerMenu(player, wrapper)
                NMSManager.nms.initMenu(player, wrapper)
            }
        }
        super.open(player, initialize)

        NMSManager.nms.sendOpenScreenPacket(player, type, title)
        update(player.uniqueId)
    }

    override fun update(viewer: UUID) {
        val player = Bukkit.getPlayer(viewer) ?: return

        updateItems(player)
        NMSManager.nms.sendContentsPacket(player, items)
        wrappers[player.uniqueId]?.clearItems()
        wrappers[player.uniqueId]?.setItem(1, ItemStack(Material.BARRIER))
    }

    override fun close(player: Player) {
        if (!packetBased) {
            wrappers.remove(player.uniqueId)
            Bukkit.getScheduler().runTask(CassiniConfig.plugin, Runnable {
                NMSManager.nms.closeContainerMenu(player)
            })
        }
        super.close(player)
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

    /**
     * Setups up wrapper.
     */
    protected open fun setupWrapper(player: Player) {
        wrappers[player.uniqueId] = NMSManager.nms.createChestMenu(player, type, title)
    }

}