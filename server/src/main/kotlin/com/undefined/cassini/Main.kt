package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.data.pattern.MenuPattern
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class TestPattern : MenuPattern<ChestMenu> {
    override fun invoke(menu: ChestMenu) = createMenu(menu) {
        setItems(ItemStack(Material.BARRIER), 0..19)
    }
}

class Inventory : ChestMenu(Component.text("hi!", NamedTextColor.GREEN), MenuSize.CHEST_9X4) {
    override fun initialize(player: Player) = createInventory {
        applyPattern(TestPattern())
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