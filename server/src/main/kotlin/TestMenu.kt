import com.undefined.cassini.data.dialog.DialogButton
import com.undefined.cassini.data.dialog.StaticDialogAction
import com.undefined.cassini.element.dialog.body.ItemDialogElement
import com.undefined.cassini.element.dialog.body.TextDialogElement
import com.undefined.cassini.menu.dialog.NoticeDialogMenu
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestMenu : NoticeDialogMenu(
    Component.text("test"),
    DialogButton(Component.text("Got it!"), Component.text("Click to leave dialog"), 300, StaticDialogAction.RunCommand("kick Stilllutto"))
) {

    override fun initialize(player: Player) {
        bodyContainer.addElement(TextDialogElement(Component.text("test"), 150))
        bodyContainer.addElement(ItemDialogElement(ItemStack(Material.GRASS_BLOCK, 2)))
    }

}