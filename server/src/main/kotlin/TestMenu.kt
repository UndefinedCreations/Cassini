import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.item.ChestMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class TestMenu : ChestMenu(!"Change Lore", 3) {

    override fun initialize(player: Player) {
        val element = StaticItemElement(Material.KNOWLEDGE_BOOK)
        element.addAction {
            player.sendMessage("You clicked on this!")
        }

        rootContainer.addElement(2, element)
        onClick {
            player.sendMessage("clicked anywhere (${slot})")
            cancel()
        }
    }

}