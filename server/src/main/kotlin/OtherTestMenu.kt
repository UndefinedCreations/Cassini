import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.Menu
import com.undefined.cassini.menu.item.ChestMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class OtherTestMenu(parent: Menu<*, *>? = null) : ChestMenu(!"Change Lore", 3, parent) {

    override fun initialize(player: Player) {
        preventClicking()

        val otherElement = StaticItemElement(Material.RED_CONCRETE) {
            back()
        }
        setElement(13, otherElement)
    }

}