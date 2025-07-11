import com.undefined.cassini.data.dialog.DialogListOption
import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.element.dialog.input.BooleanDialogInput
import com.undefined.cassini.element.dialog.input.ListDialogInput
import com.undefined.cassini.data.dialog.MultiLineOptions
import com.undefined.cassini.element.dialog.input.SliderDialogInput
import com.undefined.cassini.element.dialog.input.TextDialogInput
import com.undefined.cassini.menu.dialog.DialogListDialogMenu
import com.undefined.cassini.menu.dialog.DialogMenu
import com.undefined.cassini.menu.dialog.NoticeDialogMenu
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.UUID

class TestMenu : DialogListDialogMenu(
    Component.text("Dialog!"),
    listOf(NoticeDialogMenu(!"Notice!"), NoticeDialogMenu(!"Install Stellar!")),
    DialogButton(Component.text("Yeah man!"), Component.text("Click to yes-and.")),
    0b1,
) {

    override fun initialize(player: Player) {
//        bodyContainer.addElement(TextDialogBody(Component.text("test"), 150))
//        bodyContainer.addElement(ItemDialogBody(ItemStack(Material.GRASS_BLOCK, 2)))
        inputContainer.addElement(TextDialogInput("key", Component.text("Label"), initial = "This is a test!", multiline =  MultiLineOptions(32, 2)))
        inputContainer.addElement(BooleanDialogInput("key", Component.text("test"), true))
        val options: MutableList<DialogListOption> = mutableListOf()
        options.add(DialogListOption("id", Component.text("test"), true))
        for (i in 0..3) options.add(DialogListOption(UUID.randomUUID().toString(), Component.text(i.toString()), false))
        inputContainer.addElement(ListDialogInput("key", Component.text("test"), options = options))
        inputContainer.addElement(SliderDialogInput("key", Component.text("test"), 1F, 100F, null, 75F, "%s, %s"))
    }

}