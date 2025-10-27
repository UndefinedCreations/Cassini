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
            setElement(6, StaticItemElement(Material.DIAMOND))
        }

        val container = ItemContainerImpl(3, 3)
        for (slot in 0..<(container.width * container.height)) {
            container.setElement(slot, StaticItemElement(Material.REDSTONE))
        }
        addContainer(container)

//        val iterator = BoxSlotIterator(this, 0, 2, 2)
//        for ((i, _) in iterator) {
//            setElement(i, StaticItemElement(Material.REDSTONE))
//        }
//        update()
//        val yayElement = StaticItemElement(Material.PAPER) {
//            val element = element as? StaticItemElement ?: return@StaticItemElement
//            element.item.editMeta { meta ->
//                meta.displayName(!"<green>YAY!")
//            }
//            element.update()
//        }
//        setElement(13, yayElement)
//
//        val otherElement = StaticItemElement(Material.RED_CONCRETE) {
//            back()
//        }
//        setElement(14, otherElement)
//
//        val randomElement = StaticItemElement(Material.GREEN_CONCRETE) {
//            player.openMenu(OtherTestMenu(this@TestMenu))
//        }
//        setElement(12, randomElement)
    }

}