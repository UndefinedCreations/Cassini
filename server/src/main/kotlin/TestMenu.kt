import com.undefined.cassini.container.item.ItemContainerImpl
import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.Menu
import com.undefined.cassini.menu.item.ChestMenu
import com.undefined.cassini.menu.item.iterator.BoxSlotIterator
import com.undefined.cassini.util.openMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class TestMenu(parent: Menu<*, *>? = null) : ChestMenu(!"Change Lore", 3, parent) {

    override fun initialize(player: Player) {
        preventClicking()
        createPattern {
            fillBorder(StaticItemElement(Material.GLASS))
//            fill(StaticItemElement(Material.STONE))
//            fillRow(1, StaticItemElement(Material.GOLD_BLOCK))
//            fillColumn(4, StaticItemElement(Material.EMERALD_BLOCK))
        }
    }

}