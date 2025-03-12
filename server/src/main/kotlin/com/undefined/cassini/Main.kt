package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.extensions.AnvilInputMenu
import com.undefined.cassini.extensions.AnvilSlot
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("test")
            .addExecution<Player> {
                val itemInMainHand = sender.inventory.itemInMainHand
                if (itemInMainHand.amount < 0 || itemInMainHand.type == Material.AIR) return@addExecution sender.sendMessage("${ChatColor.RED}You don't have anything in your main hand.")
                sender.openMenu(AnvilInputMenu.Builder("Rename")
                    .leftItem(itemInMainHand.clone())
                    .onClick(AnvilSlot.OUTPUT) {
                        itemInMainHand.itemMeta = itemInMainHand.itemMeta?.apply { setDisplayName(menu.text) }
                        sender.sendMessage("${ChatColor.GREEN}Successfully changed item name.")
                        isCancelled = true
                        close()
                    }
                    .build())
            }
            .register(this)
    }
}