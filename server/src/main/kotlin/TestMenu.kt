import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestMenu : PaginatedChestMenu(!"Change Lore", 3) {

    override fun initialize(player: Player) {
        availableSlots = SlotIterator.of(this, 0..17)
        preventClicking()

        for (material in Material.entries.filter { it.isItem && !it.isAir && !it.name.contains("LEGACY", true) }) {
            val element = StaticItemElement(material)
            rootContainer.addPaginatedElement(element)
        }

        rootContainer.setElement(18, StaticItemElement(Material.PAPER) {
            previous()
        })

        rootContainer.setElement(26, StaticItemElement(Material.PAPER) {
            next()
        })
    }

}