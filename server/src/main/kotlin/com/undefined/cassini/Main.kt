package com.undefined.cassini

import com.undefined.stellar.StellarCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        val main = StellarCommand("test")
        main.addArgument("other")
        main.addArgument("test")
            .addRequirement(4)
            .addExecution<Player> {

            }
            .register(this)
    }
}