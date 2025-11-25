package com.undefined.cassini.menu.item

import com.undefined.cassini.CassiniConfig
import com.undefined.cassini.container.item.ItemContainerImpl
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.data.item.ClickData
import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.internal.NMS1_21_8
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

    val _items: Array<ItemStack?> = arrayOfNulls(type.size!! ) // TODO make AIR items just be null (also, remove the items var, it's a temporary variable)
    val items: List<ItemStack>
        get() = _items.map { it ?: ItemStack.empty() }

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
        }
        super.open(player, initialize)

        NMSManager.nms.closeContainerMenu(player)
        if (packetBased) NMSManager.nms.sendOpenScreenPacket(player, type, title)

        if (initialize && !packetBased) setupWrapper(player)
//        if (initialize && !packetBased) {
//            if (wrappers[player.uniqueId] == null) setupWrapper(player)
//            val wrapper = wrappers[player.uniqueId] ?: error("Wrapper is null??!")
//            println(1)
//            NMSManager.nms.setContainerMenu(player, wrapper)
//            NMSManager.nms.initMenu(player, wrapper)
//        }
        wrappers[player.uniqueId]?.open(player)

        update(player.uniqueId)
    }

    override fun update(viewer: UUID) {
        val player = Bukkit.getPlayer(viewer) ?: return

        wrappers[player.uniqueId]?.clearItems()
        updateItems(player)
        if (packetBased) NMSManager.nms.sendContentsPacket(player, items)
    }

    override fun close(player: Player) {
        println("ItemMenu.close")
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
        _items.fill(null)
        for ((i, item) in getItems(player).map { if (it.isEmpty) null else it }.withIndex()) {
            _items[i] = item
            wrappers[player.uniqueId]?.setItem(i, item)
        }
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