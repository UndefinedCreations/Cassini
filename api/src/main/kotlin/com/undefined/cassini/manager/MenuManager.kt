package com.undefined.cassini.manager

import com.undefined.cassini.Cassini
import com.undefined.cassini.ContainerMenu
import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuConfig
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
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import org.jetbrains.annotations.ApiStatus

object MenuManager : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, Cassini.plugin)
    }

    private val hasBeenInitialized: MutableSet<Menu<*>> = mutableSetOf()
    @ApiStatus.Internal val menus: HashMap<Int, Menu<*>> = hashMapOf()
    @ApiStatus.Internal val wrappers: HashMap<Int, MenuWrapper> = hashMapOf()
    val version by lazy { Bukkit.getBukkitVersion().split("-").first() }
    val nms: NMS by lazy { versions[version] ?: throw UnsupportedVersionException(versions.keys) }
    val packetManager: PacketManager by lazy { PacketManagerImpl() }
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
        if (!modifySlots) nms.sendContainerClosePacket(player, wrapper)
        menu.onClose(player)
        nms.sendOpenScreenPacket(player, wrapper)
        nms.initMenu(player, wrapper)
        nms.setContainerMenu(player, wrapper)
        for (slot in 0..menu.size)
            menu.items[slot]?.itemStack?.let { wrapper.setItem(slot, it) }
        onOpen(player, menu, wrapper)
    }

    fun openAnvilMenu(player: Player, menu: AnvilMenu, modifySlots: Boolean = Cassini.modifySlots) {
        update(player, menu)
        val wrapper = nms.createAnvilMenu(player, menu.size, menu.title, MenuConfig(modifySlots))
        if (!modifySlots) nms.sendContainerClosePacket(player, wrapper)
        menu.onClose(player)
        nms.sendOpenScreenPacket(player, wrapper)
        nms.initMenu(player, wrapper)
        nms.setContainerMenu(player, wrapper)
        for (slot in 0..menu.size)
            menu.items[slot]?.itemStack?.let { wrapper.setItem(slot, it) }
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
        println(meta.spigot().pages)
        println(menu.pages)
        book.itemMeta = meta

        val hand: ItemStack = player.inventory.itemInMainHand
        player.inventory.setItemInMainHand(book)
        nms.sendOpenBookPacket(player, book)
        player.inventory.setItemInMainHand(hand)
    }

    private fun onOpen(player: Player, menu: ContainerMenu<*>, wrapper: MenuWrapper) {
        menus[wrapper.id] = menu
        wrappers[wrapper.id] = wrapper
        menu.onOpen(player)
    }

}