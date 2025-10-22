import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ChestMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class TestMenu : ChestMenu(!"Change Lore", 3) {

    override fun initialize(player: Player) {
        val element = ItemElement(2, Material.KNOWLEDGE_BOOK)
        element.addAction {
            player.sendMessage("You clicked on this!")
        }

        rootContainer.addElement(element)
        onClick {
            player.sendMessage("fukcy ou (${slot})")
            cancel()
        }
    }

}