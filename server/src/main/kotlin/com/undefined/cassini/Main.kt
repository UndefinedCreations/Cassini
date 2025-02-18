package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.impl.BookMenu
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
    override fun initialize(player: Player) = create {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta ?: return@create
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
                val menu = BookMenu.builder()
                    .addPage("<green>Test", "another test")
                    .addPage("test")
                    .build()
                sender.openMenu(menu)
            }
            .register(this)
    }
}