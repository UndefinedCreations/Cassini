package com.undefined.cassini.v1_21_3

import com.undefined.cassini.handlers.MenuHandler
import com.undefined.cassini.impl.ChestMenu
import net.minecraft.core.NonNullList
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.world.item.ItemStack
import org.bukkit.entity.Player


object MenuHandler : MenuHandler() {

    override fun openInventory(player: Player, menu: ChestMenu) {
        val type = MenuAdapter.getMenuType(menu.size)
        val syncId = -1
        val title = ComponentAdapter.getComponent(menu.title)
        val packet = ClientboundOpenScreenPacket(syncId, type, title)
        player.connection().sendPacket(packet)
        println("contents: ${menu.items.map { it.value.itemStack.type }}")
        setContents(player, menu)
    }

    fun setContents(player: Player, menu: ChestMenu) {
        val items = NonNullList.create<ItemStack>()
        println("size: ${menu.size}")
        for (slot in 0..menu.size) {
            println("slot: $slot")
            println("item: ${items.getOrNull(slot)}")
            menu.items.getOrDefault(slot, null)?.let { items.add(ItemAdapter.getMojangItemStack(it)) } ?: items.add(ItemAdapter.empty)
        }
        println("items: ${items.map { it.item.name.toString() }}")
        println("gui items: ${menu.items.map { "${it.key}: ${it.value.itemStack.type.name}" }}")
        val serverPlayer = player.serverPlayer()

        val syncId = -1
        val packet = ClientboundContainerSetContentPacket(
            syncId,
            serverPlayer.containerMenu.stateId,
            items,
            ItemAdapter.empty
        )

        player.connection().sendPacket(packet)
    }

}