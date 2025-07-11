import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.element.dialog.input.BooleanDialogInput
import com.undefined.cassini.element.dialog.input.MultiLineOptions
import com.undefined.cassini.element.dialog.input.TextDialogInput
import com.undefined.cassini.menu.dialog.MultiActionDialogMenu
import com.undefined.cassini.util.openMenu
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class TestMenu : MultiActionDialogMenu(
    Component.text("Dialog!"),
    listOf(
        DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")).also { button ->
            button.addAction { player ->
                player.openMenu(TestMenu())
            }
        },
    ),
    0b1,
    DialogButton(Component.text("EXIT BITCH!")),
) {

    override fun initialize(player: Player) {
//        bodyContainer.addElement(TextDialogBody(Component.text("test"), 150))
//        bodyContainer.addElement(ItemDialogBody(ItemStack(Material.GRASS_BLOCK, 2)))
        inputContainer.addElement(TextDialogInput("key", Component.text("Label"), 300, true, "This is a test!", 300, MultiLineOptions(256, 5)))
        inputContainer.addElement(BooleanDialogInput("key", Component.text("test"), true))
    }

}