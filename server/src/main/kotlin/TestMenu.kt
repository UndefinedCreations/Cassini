import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.item.ChestMenu
import com.undefined.cassini.util.openMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

class TestMenu : ChestMenu(!"Change Lore", 3) {

    override fun initialize(player: Player) {
        preventClicking()

        val element = StaticItemElement(Material.KNOWLEDGE_BOOK)
        element.addAction {
            val uuid = UUID.randomUUID().toString()
            player.sendMessage("You clicked on this! ($uuid)")
            val meta = element.item.itemMeta
            meta.customName(!uuid)
            element.item.itemMeta = meta
            update()
//            player.openMenu(TestDialogMenu())
        }

        onClick {
            cancel()
        }

        rootContainer.addElement(2, element)
    }

}