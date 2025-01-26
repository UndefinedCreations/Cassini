package com.undefined.cassini

import com.undefined.cassini.data.CassiniContext
import com.undefined.cassini.data.click.ClickActions
import com.undefined.cassini.data.MenuSize
import com.undefined.cassini.data.item.GUIItem
import com.undefined.cassini.data.item.PageItem
import com.undefined.cassini.impl.PaginatedMenu
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Inventory : PaginatedMenu(Component.text("hi!", NamedTextColor.GREEN), MenuSize.CHEST_9X4, {
    val list: MutableList<GUIItem> = mutableListOf()
    for (i in 0..100) {
        val mat = Material.entries.filter { it.isItem }.random()
        list.add(GUIItem.fromMaterial(mat))
    }
    list
}) {

    override val backButton: PageItem
        get() = PageItem(0, ItemStack(Material.RED_WOOL))
    override val nextButton: PageItem
        get() = PageItem(8, ItemStack(Material.GREEN_WOOL))

    override fun initialize(player: Player) = createInventory {
        setItems(ItemStack(Material.BLACK_STAINED_GLASS_PANE), 0..8)
        val item = GUIItem(ItemStack(Material.BARRIER))
        item.addAction(ClickActions.CLOSE)
        setItem(4, item)
    }

    override fun onClick(context: CassiniContext) {
        println("onClick: ${context.player.name}, ${context.type}")
    }

    override fun onOpen(player: Player) {
        println("onOpen: ${player.name}")
    }

    override fun onClose(player: Player) {
        println("onClose: ${player.name}")
    }
}

class Main : JavaPlugin() {
    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("test")
            .addExecution<Player> {
                sender.openMenu(Inventory())
            }
            .register(this)
    }
}