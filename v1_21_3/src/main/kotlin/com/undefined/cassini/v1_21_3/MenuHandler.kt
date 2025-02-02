package com.undefined.cassini.v1_21_3

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.event.MenuCloseEvent
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.handlers.MenuHandler
import net.minecraft.core.NonNullList
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.world.inventory.AnvilMenu
import net.minecraft.world.inventory.ChestMenu
import net.minecraft.world.item.ItemStack
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta

object MenuHandler : MenuHandler() {

    val menus: HashMap<Id, MenuConfig> = hashMapOf()

    override fun openMenu(player: Player, menu: Menu, modifySlots: Boolean) {
        val serverPlayer = player.serverPlayer()
        val connection = player.connection()

        val type = MojangAdapter.getMenuType(menu)
        val id = player.serverPlayer().nextContainerCounter()
        val title = MojangAdapter.getComponent(menu.title)

        val previousMenu = menus.values.firstOrNull { it.player == player }
        previousMenu?.let {
            if (modifySlots) return setContents(player, menu)

            connection.sendPacket(ClientboundContainerClosePacket(id))
            onClose(player, menu)
        }

        connection.sendPacket(ClientboundOpenScreenPacket(id, type, title))
        menus[id] = MenuConfig(player, menu, id, modifySlots)

        when (menu.optimization) {
            MenuOptimization.NORMAL -> {
                val container = ChestMenu(
                    type,
                    id,
                    serverPlayer.inventory,
                    CraftInventoryCustom(player, menu.size, menu.title).inventory,
                    menu.size / 8
                )

                for ((slot, item) in menu.items)
                    container.setItem(slot, serverPlayer.containerMenu.stateId, MojangAdapter.getMojangItemStack(item))
                serverPlayer.containerMenu = container
                serverPlayer.initMenu(container)
            }
            MenuOptimization.FAST -> {
                val container = MojangAdapter.getContainer(menu, type, id, player)
                serverPlayer.containerMenu = container
                serverPlayer.initMenu(container)
                setContents(player, menu)
            }
            MenuOptimization.FASTEST -> {
                setContents(player, menu)
            }
        }
    }

    override fun setContents(player: Player, menu: Menu) {
        val items = NonNullList.create<ItemStack>()
        for (slot in 0..menu.size)
            menu.items.getOrDefault(slot, null)?.let { items.add(MojangAdapter.getMojangItemStack(it)) }
                ?: items.add(MojangAdapter.emptyItem)
        val serverPlayer = player.serverPlayer()

        val containerId = menus.filterValues { it.menu == menu }.keys.firstOrNull() ?: serverPlayer.nextContainerCounter()
        val packet = ClientboundContainerSetContentPacket(
            containerId,
            serverPlayer.containerMenu.stateId,
            items,
            MojangAdapter.emptyItem
        )

        player.connection().sendPacket(packet)
    }

    override fun setAnvilText(player: Player, text: String) {
        val container = player.serverPlayer().containerMenu as? AnvilMenu ?: return
        container.setItemName(text)
    }

    override fun registerListeners() { PacketListener }

    fun onClose(player: Player, menu: Menu) {
        val previousMenu = menus.values.firstOrNull { it.player == player }
        previousMenu?.let { menus.remove(previousMenu.id) }
        sync {
            Bukkit.getPluginManager().callEvent(MenuCloseEvent(player, menu))
        }
    }

}