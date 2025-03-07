package com.undefined.cassini.manager

import com.undefined.cassini.Cassini
import com.undefined.cassini.ContainerMenu
import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.event.MenuOpenEvent
import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.BookMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.nms.NMS
import com.undefined.cassini.nms.PacketListener
import com.undefined.cassini.nms.PacketManager
import com.undefined.cassini.nms.wrapper.MenuWrapper
import com.undefined.cassini.v1_21_4.PacketListener1_21_4
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import org.jetbrains.annotations.ApiStatus

object MenuManager {

    private val hasBeenInitialized: MutableSet<Menu<*>> = mutableSetOf()
    @ApiStatus.Internal val menus: HashMap<Int, Menu<*>> = hashMapOf()
    @ApiStatus.Internal val wrappers: HashMap<Int, MenuWrapper> = hashMapOf()
    val nms: NMS by lazy { versions[version] ?: throw UnsupportedVersionException(versions.keys) }
    val packetManager: PacketManager by lazy { PacketManagerImpl() }
    private val version by lazy { Bukkit.getBukkitVersion().split("-").first() }
    private val versions: Map<String, NMS> = mapOf(
        "1.21.4" to com.undefined.cassini.v1_21_4.NMS1_21_4
    )

    fun getPacketListener(): PacketListener = when (version) {
        "1.21.4" -> PacketListener1_21_4.getInstance(packetManager)
        else -> throw UnsupportedVersionException(listOf("1.21.4"))
    }

    fun update(player: Player, menu: Menu<*>) {
        if (menu !in hasBeenInitialized) hasBeenInitialized.add(menu)
        if (menu is ContainerMenu<*>) menu.items.clear()
        menu.preinitialize(player)
        menu.initialize(player)
        menu.afterinitialize(player)
    }

    fun openChestMenu(player: Player, menu: ChestMenu, modifySlots: Boolean = Cassini.modifySlots) {
        update(player, menu)
        val wrapper = nms.createChestMenu(player, menu.size, menu.title, MenuConfig(modifySlots))
        openContainerMenu(player, menu, wrapper, modifySlots)
        onOpen(player, menu, wrapper)
    }

    fun openAnvilMenu(player: Player, menu: AnvilMenu, modifySlots: Boolean = Cassini.modifySlots) {
        val wrapper = nms.createAnvilMenu(player, menu.size, menu.title, MenuConfig(modifySlots))
        openContainerMenu(player, menu, wrapper, modifySlots)
        menu.cost?.let { wrapper.itemCost = it }
        onOpen(player, menu, wrapper)
    }

    fun openBookMenu(player: Player, menu: BookMenu) {
        update(player, menu)
        val book = ItemStack(Material.WRITTEN_BOOK)
        val meta: BookMeta = book.itemMeta as BookMeta
        meta.title = "A Custom Cassini Book Menu."
        meta.author = "Undefined Creations"
        for (page in menu.pages) meta.spigot().addPage(page.toBungee())
        book.itemMeta = meta

        val hand: ItemStack = player.inventory.itemInMainHand
        player.inventory.setItemInMainHand(book)
        nms.sendOpenBookPacket(player, book)
        player.inventory.setItemInMainHand(hand)
    }

    private fun openMenu(player: Player, menu: Menu<*>, wrapper: MenuWrapper) {
        if (!onOpen(player, menu, wrapper)) return
        update(player, menu)
    }

    private fun openContainerMenu(player: Player, menu: ContainerMenu<*>, wrapper: MenuWrapper, modifySlots: Boolean = Cassini.modifySlots) {
        openMenu(player, menu, wrapper)
        if (!modifySlots) nms.sendContainerClosePacket(player, wrapper)
        menu.onClose(player)
        nms.sendOpenContainerScreenPacket(player, wrapper)
        nms.initMenu(player, wrapper)
        nms.setContainerMenu(player, wrapper)
        for (slot in 0..menu.size)
            menu.items[slot]?.itemStack?.let { wrapper.setItem(slot, it) }
    }

    private fun onOpen(player: Player, menu: Menu<*>, wrapper: MenuWrapper): Boolean { // whether to continue
        menus[wrapper.id] = menu
        wrappers[wrapper.id] = wrapper
        menu.onOpen(player)
        return !MenuOpenEvent(player, menu).apply { callEvent() }.isCancelled
    }

}