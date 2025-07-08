import com.undefined.cassini.data.dialog.DialogButton
import com.undefined.cassini.data.dialog.StaticDialogAction
import com.undefined.cassini.element.dialog.body.ItemDialogElement
import com.undefined.cassini.element.dialog.body.TextDialogElement
import com.undefined.cassini.menu.dialog.ConfirmationDialogMenu
import com.undefined.cassini.menu.dialog.NoticeDialogMenu
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestMenu : ConfirmationDialogMenu(
    Component.text("Dialog!"),
    DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")).also {
        it.addAction { player -> player.sendMessage("Yay!!!") }
    },
    DialogButton(Component.text("Nah bro..."), Component.text("Click to die.")).also {
        it.addAction { player -> player.sendMessage("Yay!!!") }
    }
) {

    override fun initialize(player: Player) {
        bodyContainer.addElement(TextDialogElement(Component.text("test"), 150))
        bodyContainer.addElement(ItemDialogElement(ItemStack(Material.GRASS_BLOCK, 2)))
    }

}