package com.undefined.cassini

import com.undefined.cassini.impl.SmithingMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.entity.Player
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
                        isCancelled = false
                        player.sendMessage("slot: $slot")
                        if (slot != 3) return@onClick

                        player.sendMessage(items[0].translationKey)
                        player.sendMessage(items[0].type.name)
                        val material = Registry.TRIM_MATERIAL.get(NamespacedKey.minecraft(items[0].translationKey)) ?: TrimMaterial.AMETHYST // should be getOrThrow
                        player.sendMessage("Material: ${material.key}")


                        player.sendMessage(items[2].translationKey)
                        player.sendMessage(items[2].type.name)
                        val pattern = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft(items[2].translationKey)) ?: TrimPattern.BOLT
                        player.sendMessage("Pattern: ${pattern.key}")

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