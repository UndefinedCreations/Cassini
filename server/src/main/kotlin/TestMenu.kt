import com.undefined.cassini.element.item.ItemElement
import com.undefined.cassini.menu.item.ChestMenu
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player

class TestMenu : ChestMenu(Component.text("test"), 3) {

    override fun initialize(player: Player) {
        rootContainer.addElement(ItemElement(Material.ANVIL))
    }

}