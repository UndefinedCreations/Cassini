package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.extensions.AnvilInputMenu
import com.undefined.cassini.extensions.AnvilSlot
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.impl.SmithingMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("trim")
            .addExecution<Player> {
                val itemInMainHand = sender.inventory.itemInMainHand
                if (itemInMainHand.amount < 0 || itemInMainHand.type == Material.AIR) return@addExecution sender.sendMessage("${ChatColor.RED}You don't have anything in your main hand.")

                sender.openMenu(SmithingMenu.Builder("Trim")
                    .setInputItem(itemInMainHand.clone())
                    .onClick {
                        val pattern = menu.items[2]!!.itemStack as ItemStack
                        val material = menu.items[0]!!.itemStack

                        val meta = itemInMainHand.itemMeta as? ArmorMeta ?: return@onClick sender.sendMessage("You don't have an armor piece in your hand.")
                        meta.trim = ArmorTrim(material, pattern)
                        itemInMainHand.itemMeta = meta

                        isCancelled = true
                        close()
                    }
                    .build())
            }
            .register(this)
    }
}