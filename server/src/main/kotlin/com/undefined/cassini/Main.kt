package com.undefined.cassini

import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.impl.AnvilMenu
import com.undefined.cassini.impl.ChestMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.wesjd.anvilgui.AnvilGUI
import net.wesjd.anvilgui.AnvilGUI.ResponseAction
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class TestAnvilMenu : AnvilMenu(Component.text("Player", NamedTextColor.RED)) {
    override fun initialize(player: Player) = createInventory {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta ?: return@createInventory
        meta.setDisplayName("Test")
        item.itemMeta = meta
        setInputLeftItem(item)
        setOutputItem(item)
//        setText("Default text.")
    }
}

enum class Type() {
    SPAWNER,
    ;
}

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("test")
            .addExecution<Player> {
                val menu = ChestMenu.Builder("chest", MenuSize.CHEST_9X2)
                    .onClick {
                        player.sendMessage("Hello!")
                    }.build()
                sender.openMenu(menu)
//                AnvilGUI.Builder()
//                    .itemLeft(ItemStack(Material.PAPER))
//                    .text("Default text")
//                    .onClick { _: Int, _: AnvilGUI.StateSnapshot ->
//                        return@onClick listOf(ResponseAction.close())
//                    }
//                    .plugin(this@Main)
//                    .open(sender)
            }
            .register(this)
    }
}