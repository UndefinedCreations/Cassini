import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.Menu
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import com.undefined.cassini.util.openMenu
import org.bukkit.Material
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestMenu(parent: Menu<*, *>? = null) : PaginatedChestMenu(!"Change Lore", 3, parent) {

    var hasInitialized = false
//    override val packetBased: Boolean = true

    override fun initialize(player: Player) {
        availableSlots = SlotIterator.of(this, 0..17)

        if (!hasInitialized) {
            for (material in Material.entries.filter { it.isItem && !it.isAir && !it.name.contains("LEGACY", true) }.shuffled().take(2)) {
                val element = StaticItemElement(material)
                addPaginatedElement(element)
            }
        }

        setElement(22, StaticItemElement(Material.GREEN_CONCRETE) {
//            player.openMenu(TestMenu(this@TestMenu))
            val inventory = CraftInventoryCustom(player, type.size!!, title)
            inventory.setItem(2, ItemStack(Material.DIAMOND))
            player.openInventory(inventory)
        })

        setElement(18, StaticItemElement(Material.PAPER) {
            previous()
        })

        setElement(26, StaticItemElement(Material.PAPER) {
            next()
        })

        hasInitialized = true
    }

    override fun onClose(player: Player) {
        player.sendMessage(elements[0]?.getItem(player)?.i18NDisplayName ?: "null")
    }

}