package com.undefined.cassini.v1_21_3.wrapper

import com.undefined.cassini.data.MenuConfig
import com.undefined.cassini.nms.wrapper.AnvilMenuWrapper
import com.undefined.cassini.v1_21_3.MojangAdapter
import com.undefined.cassini.v1_21_3.serverPlayer
import net.kyori.adventure.text.Component
import net.minecraft.world.inventory.AnvilMenu
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@Suppress("ClassName")
class AnvilMenuWrapper1_21_3(
    player: Player,
    override val size: Int,
    override val title: Component,
    override val config: MenuConfig
) : AnvilMenu(
    player.serverPlayer().nextContainerCounter(),
    player.serverPlayer().inventory
), AnvilMenuWrapper {

    init {
        this.checkReachable = false
        setTitle(MojangAdapter.getComponent(title))
    }

    override fun createResult() {
        val output = getSlot(2)
        output.set(getSlot(0).item.copy())
    }

    override val id: Int = containerId
    override var text: String?
        get() = this.itemName
        set(value) {
            setItemName(value ?: "")
        }

    override fun setItem(slot: Int, item: ItemStack) = setItem(slot, stateId, CraftItemStack.asNMSCopy(item))

}