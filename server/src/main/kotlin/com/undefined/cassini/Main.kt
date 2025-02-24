package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.data.item.PageItem
import com.undefined.cassini.extensions.PaginatedMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        val main = StellarCommand("test")
        main.addArgument("test")
            .addExecution<Player> {
                sender.openMenu(object : PaginatedMenu(Component.text("hi1"), MenuSize.CHEST_9X3, {
                    listOf()
                }) {
                    override val backButton = PageItem(size - 8, ItemStack(Material.RED_STAINED_GLASS_PANE))
                    override val nextButton = PageItem(size, ItemStack(Material.GREEN_STAINED_GLASS_PANE))

                })
            }
            .register(this)
    }
}