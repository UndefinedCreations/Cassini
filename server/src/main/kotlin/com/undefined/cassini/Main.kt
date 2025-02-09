package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.extensions.AnvilInputMenu
import com.undefined.cassini.extensions.AnvilSlot
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class TestAnvilMenu : ChestMenu(Component.text("Player", NamedTextColor.RED), MenuSize.CHEST_9X3) {
    override fun initialize(player: Player) = createInventory {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta ?: return@createInventory
        meta.setDisplayName("Test")
        item.itemMeta = meta
        setItem(0, item)
    }
}

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("test")
            .addExecution<Player> {
                val menu = AnvilInputMenu.Builder("anvil")
                    .leftItem(ItemStack(Material.PAPER))
                    .onClick(AnvilSlot.OUTPUT) {
                        player.sendMessage("Hello!")
                    }.build()
                sender.openMenu(menu)
            }
            .register(this)
    }
}