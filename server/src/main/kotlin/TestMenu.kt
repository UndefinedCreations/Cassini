import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import org.bukkit.Material
import org.bukkit.entity.Player

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

        val randomElement = StaticItemElement(Material.PAPER) {
            val element = element as? StaticItemElement ?: return@StaticItemElement
            element.item.editMeta { meta ->
                meta.displayName(!"<gray>Yay!")
            }
            element.update()
        }
        rootContainer.setElement(22, randomElement)
    }

}