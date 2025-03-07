package com.undefined.cassini.v1_21_4.wrapper

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.wrapper.AnvilMenuWrapper
import com.undefined.cassini.v1_21_4.MojangAdapter
import com.undefined.cassini.v1_21_4.NMS1_21_4.serverPlayer
import com.undefined.cassini.v1_21_4.PacketListener1_21_4
import net.kyori.adventure.text.Component
import net.minecraft.world.inventory.AnvilMenu
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@Suppress("ClassName")
class AnvilMenuWrapper1_21_4(
    val bukkitPlayer: Player,
    override val size: Int,
    title: Component,
    override val config: MenuConfig
) : AnvilMenu(
    bukkitPlayer.serverPlayer().nextContainerCounter(),
    bukkitPlayer.serverPlayer().inventory
), AnvilMenuWrapper {

    override var title: Component = title
        set(value) {
            field = value
        }

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
    override var text: String?
        get() = this.itemName
        set(value) {
            value?.let { setItemName(it) }
        }
    override var itemCost: Int
        get() = this.cost.get()
        set(value) {
            this.cost.set(value)
        }

    override fun setItem(slot: Int, item: ItemStack) = setItem(slot, stateId, CraftItemStack.asNMSCopy(item))

}