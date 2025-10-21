import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ChestMenu
import org.bukkit.Material
import org.bukkit.entity.Player

class TestMenu : ChestMenu(!"Change Lore", 3) {

    override fun initialize(player: Player) {
        rootContainer.addElement(ItemElement(2, Material.KNOWLEDGE_BOOK))
        onClick {
            player.sendMessage("fukcy ou (${slot})")
            cancel()
        }
    }

}