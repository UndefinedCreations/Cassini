package com.undefined.cassini.v1_21_3

import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.handlers.MenuHandler
import net.minecraft.core.NonNullList
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.world.inventory.ChestMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom
import org.bukkit.craftbukkit.inventory.util.CraftCustomInventoryConverter
import org.bukkit.entity.Player

object MenuHandler : MenuHandler() {

    val menus: HashMap<Int, Menu> = hashMapOf()

    override fun openMenu(player: Player, menu: Menu, modifySlots: Boolean) {
        val serverPlayer = player.serverPlayer()
        val type = MojangAdapter.getMenuType(menu.size)
        val containerId = player.serverPlayer().nextContainerCounter()
        val title = MojangAdapter.getComponent(menu.title)
        val packet = ClientboundOpenScreenPacket(containerId, type, title)

        player.connection().sendPacket(packet)
        menus[containerId] = menu
        when (menu.optimization) {
            MenuOptimization.NORMAL -> {
                val container = ChestMenu(
                    type,
                    containerId,
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
                val container = ChestMenu(
                    type,
                    containerId,
                    serverPlayer.inventory,
                    CraftInventoryCustom(player, menu.size, menu.title).inventory,
                    menu.size / 8
                )
                serverPlayer.containerMenu = container
                serverPlayer.initMenu(container)
                setContents(player, menu)
            }
            MenuOptimization.FASTEST -> {
                setContents(player, menu)
            }
        }
    }

    fun setContents(player: Player, menu: Menu) {
        val items = NonNullList.create<ItemStack>()
        for (slot in 0..menu.size)
            menu.items.getOrDefault(slot, null)?.let { items.add(MojangAdapter.getMojangItemStack(it)) }
                ?: items.add(MojangAdapter.emptyItem)
        val serverPlayer = player.serverPlayer()

        val containerId = menus.filterValues { it == menu }.keys.first()
        val packet = ClientboundContainerSetContentPacket(
            containerId,
            serverPlayer.containerMenu.stateId,
            items,
            MojangAdapter.emptyItem
        )

        player.connection().sendPacket(packet)
    }

    override fun registerListeners() { PacketListener }

}