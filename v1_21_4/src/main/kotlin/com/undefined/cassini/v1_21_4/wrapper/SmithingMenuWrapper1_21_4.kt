package com.undefined.cassini.v1_21_4.wrapper

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.wrapper.MenuWrapper
import com.undefined.cassini.nms.wrapper.SmithingMenuWrapper
import com.undefined.cassini.v1_21_4.MojangAdapter
import com.undefined.cassini.v1_21_4.NMS1_21_4.serverPlayer
import com.undefined.cassini.v1_21_4.PacketListener1_21_4
import net.kyori.adventure.text.Component
import net.minecraft.world.inventory.SmithingMenu
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class SmithingMenuWrapper1_21_4(
    private val bukkitPlayer: Player,
    override val size: Int,
    override var title: Component,
    override val config: MenuConfig
) : SmithingMenu(
    bukkitPlayer.serverPlayer().nextContainerCounter(),
    bukkitPlayer.serverPlayer().inventory
), SmithingMenuWrapper {

    init {
        this.checkReachable = false
        setTitle(MojangAdapter.getComponent(title))
    }

    override fun createResult() {
        super.createResult()
        PacketListener1_21_4.instance?.manager?.createResult(bukkitPlayer, id)
        sendAllDataToRemote()
        broadcastChanges()
    }

    override val id: Int = containerId
    override fun setItem(slot: Int, item: ItemStack) = setItem(slot, stateId, CraftItemStack.asNMSCopy(item))

}