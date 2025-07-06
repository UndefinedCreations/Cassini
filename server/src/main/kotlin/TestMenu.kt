import com.undefined.cassini.element.dialog.body.ItemDialogElement
import com.undefined.cassini.element.dialog.body.TextDialogElement
import com.undefined.cassini.menu.dialog.NoticeDialogMenu
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestMenu : NoticeDialogMenu(Component.text("test")) {

    override fun initialize(player: Player) {
        bodyContainer.addElement(TextDialogElement(Component.text("test"), 150))
        bodyContainer.addElement(ItemDialogElement(ItemStack(Material.GRASS_BLOCK, 2)))
    }

}