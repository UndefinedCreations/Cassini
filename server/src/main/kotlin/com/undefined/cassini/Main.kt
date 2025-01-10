package com.undefined.cassini

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.click.ClickActions
import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Inventory : ChestMenu(Component.text("hi!", NamedTextColor.GREEN), MenuSize.CHEST_9X2, MenuOptimization.FASTEST) {
    override fun initialize() = createInventory {
        setItem(1, ItemStack(Material.PAPER))
        addItem(ItemStack(Material.PAPER))
        setItem(7, ItemStack(Material.DIAMOND_AXE))
        val item = GUIItem(ItemStack(Material.BARRIER))
        item.addAction(ClickActions.CLOSE)
        setItem(4, item)
    }

    override fun onClick(context: CassiniContext) {
        println("onClick: ${context.player.name}, ${context.type}")
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