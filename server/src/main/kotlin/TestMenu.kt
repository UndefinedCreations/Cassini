import com.undefined.cassini.container.item.PaginatedItemContainerImpl
import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.Menu
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import com.undefined.cassini.util.openMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class TestMenu(parent: Menu<*, *>? = null) : PaginatedChestMenu(!"Change Lore", 3, parent) {

    override fun initialize(player: Player) {
        println("TestMenu.initialize")
        preventClicking()
        availableSlots = SlotIterator.of(this, 0..17)

        for (material in Material.entries.filter { it.isItem && !it.isAir && !it.name.contains("LEGACY", true) }.shuffled().take(2)) {
            val element = StaticItemElement(material)
            addPaginatedElement(element)
        }

        setElement(22, StaticItemElement(Material.GREEN_CONCRETE) {
            player.openMenu(OtherTestMenu(this@TestMenu))
        })

        setElement(18, StaticItemElement(Material.PAPER) {
            previous()
        })

        setElement(26, StaticItemElement(Material.PAPER) {
            next()
        })

        onClose { player ->
        }
    }

    override fun onClose(player: Player) {
    }
}