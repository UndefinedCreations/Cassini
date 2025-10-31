import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.Menu
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import org.bukkit.Material
import org.bukkit.entity.Player

class TestMenu(parent: Menu<*, *>? = null) : PaginatedChestMenu(!"Change Lore", 3, parent) {

    override fun initialize(player: Player) {
        preventClicking()
        availableSlots = SlotIterator.of(this, 0..17)

        for (material in Material.entries.filter { it.isItem && !it.isAir && !it.name.contains("LEGACY", true) }) {
            val element = StaticItemElement(material)
            addPaginatedElement(element)
        }

        setElement(18, StaticItemElement(Material.PAPER) {
            previous()
        })

        setElement(26, StaticItemElement(Material.PAPER) {
            next()
        })
    }

}