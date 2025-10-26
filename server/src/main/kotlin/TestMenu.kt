import com.undefined.cassini.element.item.StaticItemElement
import com.undefined.cassini.menu.CassiniMenu
import com.undefined.cassini.menu.item.ChestMenu
import com.undefined.cassini.util.openMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

class TestMenu(parent: CassiniMenu<*, *>? = null) : ChestMenu(!"Change Lore", 3, parent) {

    override fun initialize(player: Player) {
        println("init")
        preventClicking()

        val yayElement = StaticItemElement(Material.PAPER) {
            val element = element as? StaticItemElement ?: return@StaticItemElement
            element.item.editMeta { meta ->
                meta.displayName(!"<green>YAY!")
            }
            element.update()
        }
        setElement(13, yayElement)

        val otherElement = StaticItemElement(Material.RED_CONCRETE) {
            back()
        }
        setElement(14, otherElement)

        val randomElement = StaticItemElement(Material.GREEN_CONCRETE) {
            player.openMenu(OtherTestMenu(this@TestMenu))
        }
        setElement(12, randomElement)
    }

}