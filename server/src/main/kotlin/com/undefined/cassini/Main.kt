package com.undefined.cassini

import com.undefined.cassini.data.inventory.ChestSize
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Inventory : ChestMenu(Component.text("hi!"), ChestSize.GENERIC_9X1) {
    override fun updateTitle() {

    }
}

class Main : JavaPlugin() {

    override fun onEnable() {
        StellarCommand("test")
            .addExecution<Player> {
                sender.openMenu(Inventory())
            }
            .register(this)
    }

}