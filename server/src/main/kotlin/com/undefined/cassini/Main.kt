package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.data.click.ClickData
import com.undefined.cassini.data.item.MenuItem
import com.undefined.cassini.data.item.PageItem
import com.undefined.cassini.data.iterator.MenuIterator
import com.undefined.cassini.extensions.AnvilInputMenu
import com.undefined.cassini.extensions.AnvilSlot
import com.undefined.cassini.extensions.PaginatedMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
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
                sender.openMenu(AnvilInputMenu.Builder("Rename")
                    .leftItem(ItemStack(Material.DIAMOND_SWORD))
                    .onClick(AnvilSlot.OUTPUT) {
                        sender.sendMessage("${ChatColor.GREEN}Successfully renamed item to ${menu.text}")
                        close()
                    }
                    .build())
            }
            .register(this)
    }
}