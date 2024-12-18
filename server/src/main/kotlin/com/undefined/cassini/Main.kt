package com.undefined.cassini

import com.undefined.cassini.data.click.ClickActionType
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.inventory.ChestSize
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Inventory : ChestMenu(Component.text("hi!"), ChestSize.GENERIC_9X1) {
    override fun initialize() = createInventory {
        setItem(1, ItemStack(Material.PAPER))
        addItem(ItemStack(Material.PAPER))
        setItem(9, ItemStack(Material.GOLDEN_HORSE_ARMOR))
        val item = GUIItem(ItemStack(Material.DIAMOND_SWORD))
        item.addAction { data ->
            println(data.type.name)
            println("Custom Action!")
        }
        item.addAction(ClickActionType.CANCEL)
        setItem(8, item)
    }

    override fun onClick(data: ClickData) {
        println("onClick: ${data.player.name}, ${data.type}")
    }

    override fun onOpen(player: Player) {
        println("onOpen: ${player.name}")
    }

    override fun onClose(player: Player) {
        println("onClose: ${player.name}")
    }
}

class Main : JavaPlugin() {

    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("test")
            .addExecution<Player> {
                sender.openMenu(Inventory())
            }
            .register(this)
    }

}