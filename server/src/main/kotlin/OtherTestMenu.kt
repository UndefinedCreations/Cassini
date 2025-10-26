import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.CassiniMenu
import com.undefined.cassini.menu.item.ChestMenu
import com.undefined.cassini.menu.item.PaginatedChestMenu
import com.undefined.cassini.menu.item.iterator.SlotIterator
import com.undefined.cassini.util.openMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class OtherTestMenu(parent: CassiniMenu<*, *>? = null) : ChestMenu(!"Change Lore", 3, parent) {

    override fun initialize(player: Player) {
        preventClicking()

        val otherElement = StaticItemElement(Material.RED_CONCRETE) {
            back()
        }
        setElement(13, otherElement)
    }

}