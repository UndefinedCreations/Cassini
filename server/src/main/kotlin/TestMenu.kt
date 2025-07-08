import com.undefined.cassini.data.dialog.DialogButton
import com.undefined.cassini.element.dialog.body.ItemDialogElement
import com.undefined.cassini.element.dialog.body.TextDialogElement
import com.undefined.cassini.menu.dialog.MultiActionDialogMenu
import com.undefined.cassini.util.openMenu
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class TestMenu : MultiActionDialogMenu(
    Component.text("Dialog!"),
    listOf(
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")),
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")),
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")),
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")),
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")),
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")),
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")).also { button ->
            button.addAction { player ->
                player.openMenu(TestMenu())
            }
        },
    ),
    0b11,
    DialogButton(Component.text("EXIT BITCH!")),
) {

    override fun initialize(player: Player) {
        bodyContainer.addElement(TextDialogElement(Component.text("test"), 150))
        bodyContainer.addElement(ItemDialogElement(ItemStack(Material.GRASS_BLOCK, 2)))
    }

}