package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        val main = StellarCommand("test")
        main.addArgument("other")
        main.addArgument("test")
            .addRequirement(4)
            .addExecution<Player> {
                sender.openMenu(ChestMenu.Builder("<red>Hi!", MenuSize.CHEST_9X3)
                    .setRow(ItemStack(Material.BLACK_STAINED_GLASS_PANE), 1)
//                    .setRow(ItemStack(Material.BLACK_STAINED_GLASS_PANE), 3)
                    .setItem(4, ItemStack(Material.BARRIER))
                    .build())
            }
            .register(this)
    }
}